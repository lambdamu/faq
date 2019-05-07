import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { AppService } from '../app.service';
import { HttpErrorResponse } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class AdminGuard implements CanActivate {
    constructor(private api: AppService, private router: Router) { }

    canActivate(
        _: ActivatedRouteSnapshot,
        state: RouterStateSnapshot): Observable<boolean> {
        return this.api.authenticate(null)
            .pipe(
                catchError((error: HttpErrorResponse) => {
                    this.api.alert(error);
                    return of(false);
                }),
                map(authenticated => {
                    if (!authenticated) {
                        this.api.redirectUrl = state.url;
                        this.router.navigateByUrl('login');
                        return false;
                    }
                    if (!this.api.isAdmin()) {
                        this.router.navigateByUrl('');
                        return false;
                    }
                    return true;
                })
            );
    }
}
