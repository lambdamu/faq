import { Component, EventEmitter, Input, Output, OnChanges, SimpleChange } from '@angular/core';
import { Paging } from '../model/api-resources';

@Component({
    selector: 'app-paging',
    templateUrl: './paging.component.html',
    styleUrls: ['./paging.component.scss']
})
export class PagingComponent implements OnChanges {
    @Input() paging: Paging;
    @Output() pageSelected = new EventEmitter<number>();
    selected = false;
    private pages: number[];

    constructor() { }


    private computePages(): number[] {
        const windowSize = Math.min(10, this.paging.totalPages);
        const pages = [];
        const start = Math.abs(this.paging.number % windowSize - this.paging.number);
        for (let i = 0; i < windowSize; i++) {
            pages.push(i + start);
        }
        return pages;
    }

    ngOnChanges(changes: { [propKey: string]: SimpleChange }) {
        for (const propName in changes) {
            if (propName) {
                const propChanged = changes[propName];
                this.paging = propChanged.currentValue;
            }
        }
        this.pages = this.paging == null ? [] : this.computePages();
        this.selected = false;
    }

    pageable() {
        return this.pages.length > 0;
    }

    hasPrevious(): boolean {
        return this.paging.number > 0;
    }

    hasNext(): boolean {
        return (this.paging.number + 1) < this.paging.totalPages;
    }

    isCurrentPage(index: number): boolean {
        return this.paging.number === index;
    }

    getPages(): number[] {
        return this.pages;
    }

    selectNext() {
        if (this.hasNext()) {
            this.selectPage(this.paging.number + 1);
        }
    }

    selectPrevious() {
        if (this.hasPrevious()) {
            this.selectPage(this.paging.number - 1);
        }
    }

    selectPage(index: number) {
        if (!this.selected) {
            this.selected = true;
            this.pageSelected.emit(index);
        }
    }
}
