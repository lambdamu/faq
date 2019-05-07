import { Component, Input } from '@angular/core';
import { Paging } from '../model/api-resources';

@Component({
  selector: 'app-page-description',
  templateUrl: './page-description.component.html',
  styleUrls: ['./page-description.component.scss']
})
/**
 * Implemented as a component rather than a pipe
 * because Angular i18n is template-based.
 */
export class PageDescriptionComponent {
    @Input() paging: Paging;
    @Input() pageElementsCount: number;

    constructor() { }

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
