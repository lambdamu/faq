import { Component, Input } from '@angular/core';
import { Paging } from '../model/api-resources';

@Component({
  selector: 'app-page-description',
  templateUrl: './page-description.component.html'
})
/**
 * Implemented as a component rather than a pipe
 * because Angular i18n is template-based.
 */
export class PageDescriptionComponent {
    @Input() paging: Paging;
    @Input() pageElementsCount: number;

    constructor() { }

    pageNotFound(): boolean {
        return this.paging.number > 0 && this.paging.number >= this.paging.totalPages;
    }

    getFromElement(): number {
        return !this.paging ? 0 : this.paging.size * this.paging.number + 1;
    }

    getToElement(): number {
        return !this.paging ? 0 : this.paging.size * this.paging.number + this.pageElementsCount;
    }

    getTotalElements(): number {
        return !this.paging ? 0 : this.paging.totalElements;
    }
}
