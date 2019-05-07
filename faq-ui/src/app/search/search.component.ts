import { Component, OnInit, OnDestroy } from '@angular/core';
import { AppService } from '../app.service';
import { Page, Paging, Faqs, Faq } from '../model/api-resources';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { finalize, switchMap } from 'rxjs/operators';
import { Subscription, of } from 'rxjs';
import { Message } from '../message/message.component';

@Component({
    selector: 'app-search',
    templateUrl: './search.component.html',
    styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit, OnDestroy {
    paged: Page<Faqs> = null;
    pageSize = 5;
    query = '';
    private previousQuery = '';
    message = new Message();
    subscription: Subscription;

    constructor(private api: AppService, private router: Router, private route: ActivatedRoute) {
    }

    ngOnInit() {
        // Indexing based on 1
        this.subscription = this.route.paramMap
        .pipe(
            switchMap((params: ParamMap) => {
                const pageNo = +params.get('page');
                return of(Math.max(0, (!pageNo ? 1 : pageNo)-1));
            }))
        .subscribe(pageIndex => { this.getFaqs(pageIndex); });
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

    /**
     * @param number - page index starting from 0
     */
    private getFaqs(pageIndex: number) {
        window.scroll(0, 0);
        this.message.setLoading();
        this.previousQuery = this.query;
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
     * @param pageIndex - page index starting from 10
     */
    onPageSelected(pageIndex: number) {
        const page = pageIndex + 1;
        this.router.navigate(['search', page]);
    }

    onQueryBlur() {
        this.query = this.query.trim();
        if (!this.query) {
            this.getFaqs(0);
        }
    }

    onSearch() {
        this.query = this.query.trim();
        if (this.query !== this.previousQuery) {
            this.getFaqs(0);
        }
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
