import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { finalize, map } from 'rxjs/operators';
import { User } from './model/user';
import { Principal, Page, Faqs, Faq } from './model/api-resources';
import { Credentials } from './model/credentials';
import { Config } from './model/config';
import { AlertService } from './alert.service';
import { Message, MessageStatus } from './message/message.component';
import { LOCALE_ID } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class AppService {
    private user: User;
    private config = new Config();
    redirectUrl = 'search';
    appLocale: string;

    constructor(@Inject(LOCALE_ID) public locale: string,
                private http: HttpClient,
                private router: Router,
                private alertService: AlertService) {
        this.appLocale = locale.replace(/(\w+)-\w+/, '$1');
        this.setLocale(this.appLocale);
        this.authenticate(null).subscribe(
            _ => { },
            error => { this.alert(error); }
        );
    }

    private setLocale(locale: string) {
        const params = new HttpParams().set('lang', locale);
        this.http.get<any>(this.config.ping, { params }).subscribe(
            _ => {},
            (error: HttpErrorResponse) => { this.alert(error); }
        );
    }

    /**
     * Handle http error responses.
     * 
     * Display fatal errors via the app-wide alert service.
     * Non-fatal errors will be forwarded to the message UI or
     * to the alert service if no ui is available.
     * 
     * @param error - the http error
     * @param message - option, a message UI to display non-fatal errors
     */
    alert(error: HttpErrorResponse, message?: Message) {
        const text = AlertService.toAlertString(error);
        // Fatal error
        if (error instanceof ErrorEvent) {
            if (message) {
                message.clear();
            }
            this.alertService.add(text);
        } else if (message) {
            // Message will handle notification
            message.setServerMessage(MessageStatus.Failure, text);
        } else {
            // Caller has no UI, pipe to service alerts
            this.alertService.add(text);
        }
    }

    username(): string {
        return this.authenticated() ? this.user.name : '';
    }

    isAdmin(): boolean {
        return this.authenticated() ? this.user.isAdmin() : false;
    }

    authenticated(): boolean {
        return this.user != null;
    }

    /**
     * Authenticate the user.
     * @param credentials - may be null
     * @return authentication validation result
     */
    authenticate(credentials: Credentials): Observable<boolean> {
        // Do not call when already authenticated
        if (!credentials && this.authenticated()) {
            return of(true);
        }
        const headers = new HttpHeaders(credentials ? {
            authorization: 'Basic ' + btoa(credentials.username + ':' + credentials.password)
        } : {});

        return this.http.get<Principal>(this.config.login, { headers })
            .pipe(
                map(resource => {
                    this.user = this.toUser(resource);
                    return this.authenticated();
                },
                    (error: HttpErrorResponse) => {
                        return error;
                    }));
    }

    logout() {
        this.http.post(this.config.logout, {})
            .pipe(finalize(() => { this.user = null; }))
            .subscribe(
                _ => { this.router.navigateByUrl('/login'); },
                (error: HttpErrorResponse) => { this.alert(error); }
            );
    }

    getFaqs(page: number, size: number, search: string): Observable<Page<Faqs>> {

        const params = new HttpParams()
            .set('page', page.toString())
            .set('size', size.toString())
            .set('search', search ? search : '');

        return this.http.get<Page<Faqs>>(this.config.faqs, { params });
    }

    getFaq(id: number): Observable<Faq> {
        if (id && id > 0) {
            return this.http.get<Faq>(`${this.config.faqs}/${id}`);
        }
        return of(null);
    }

    newFaq(): Faq {
        return {
            uid: null,
            question: '',
            answer: '',
            tagset: [],
            _links: { self: { href: null } }
        };
    }

    createFaq(faq: Faq): Observable<Faq> {
        return this.http.post<Faq>(this.config.faqs, faq);
    }

    updateFaq(faq: Faq): Observable<Faq> {
        return this.http.put<Faq>(faq._links.self.href, faq);
    }

    deleteFaq(faq: Faq): Observable<Faq> {
        return this.http.delete<Faq>(faq._links.self.href);
    }

    /**
     * @param principal - JSON encoded user or empty payload
     * @return user object or null if not authenticated
     */
    private toUser(principal: Principal): User {
        let user = null;
        if (principal && principal.authenticated) {
            user = new User();
            user.name = principal.name;
            user.roles = principal.authorities.map(a => a.authority);
        }
        return user;
    }
}
