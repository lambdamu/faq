import { Component, OnInit, OnDestroy } from '@angular/core';
import { AppService } from '../app.service';
import { Page, Paging, Faqs, Faq } from '../model/api-resources';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { finalize, switchMap } from 'rxjs/operators';
import { Subscription, of } from 'rxjs';
import { Message } from '../message/message.component';

@Component({
    selector: 'app-search',
    templateUrl: './search.component.html'
})
export class SearchComponent implements OnInit, OnDestroy {
    paged: Page<Faqs> = null;
    pageSize = 5;
    query = '';
    message = new Message();
    subscription: Subscription;

    constructor(private api: AppService, private router: Router, private route: ActivatedRoute) {
    }

    ngOnInit() {
        // Indexing based on 1
        this.subscription = this.route.paramMap
        .pipe(
            switchMap((params: ParamMap) => {
                const query = params.get('query');
                return of(query);
            }))
        .subscribe(query => {
            this.query = query ? query : '';
            this.getFaqs(0);
        });
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

    private getFaqs(pageIndex: number) {
        window.scroll(0, 0);
        this.message.setLoading();
        this.api.getFaqs(pageIndex, this.pageSize, this.query)
        .pipe(finalize(() => {
          this.message.clear();
        }))
        .subscribe(
            data => { this.paged = data; },
            error => { this.api.alert(error); }
        );
    }

    isAdmin(): boolean {
        return this.api.isAdmin();
    }

    /**
     * @param pageIndex - page index starting from 0
     */
    onPageSelected(pageIndex: number) {
        this.getFaqs(pageIndex);
    }

    onSearch() {
        this.query = this.query.trim();
        if (this.query.length > 64) {
            this.query = this.query.substr(0, 64);
        }
        this.router.navigate(['search', this.query]);
    }

    faqs(): Faq[] {
        if (!this.paged || !this.paged._embedded) {
            return [];
        }
        return this.paged._embedded.faqResources;
    }

    paging(): Paging {
        return !this.paged ? null : this.paged.page;
    }

    getPageElementsCount(): number {
        return this.faqs().length;
    }

}
