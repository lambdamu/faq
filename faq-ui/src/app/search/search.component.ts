import { Component, OnInit } from '@angular/core';
import { AppService } from '../app.service';
import { Page, Paging, Faqs, Faq } from '../model/api-resources';
import { finalize } from 'rxjs/operators';
import { Message } from '../message/message.component';

@Component({
    selector: 'app-search',
    templateUrl: './search.component.html',
    styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {
    paged: Page<Faqs> = null;
    pageSize = 10;
    query = '';
    private previousQuery = '';
    message = new Message();

    constructor(private api: AppService) {
    }

    ngOnInit() {
        this.getFaqs(0);
    }

    private getFaqs(pageIndex: number) {
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

    onPageSelected(pageIndex: number) {
        this.getFaqs(pageIndex);
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
