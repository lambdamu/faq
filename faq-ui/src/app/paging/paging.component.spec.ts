import { PagingComponent } from './paging.component';

describe('PagingComponent', () => {

    it('should display nothing', () => {
        const comp = new PagingComponent();
        comp.paging = null;
        const pages = comp.computePages();
        expect(pages).toEqual([]);
    });

    it('should display one page starting from 0', () => {
        const comp = new PagingComponent();
        comp.paging = { size: 5, totalElements: 5, totalPages: 1, number: 0 };
        const pages = comp.computePages();
        expect(pages).toEqual([0]);
    });

    it('should display two pages from 0', () => {
        const comp = new PagingComponent();
        comp.paging = { size: 5, totalElements: 10, totalPages: 2, number: 0 };
        const pages = comp.computePages();
        expect(pages).toEqual([0, 1]);
    });

    it('should display 10 pages from 0', () => {
        const comp = new PagingComponent();
        comp.paging = { size: 10, totalElements: 200, totalPages: 20, number: 0 };
        const pages = comp.computePages();
        console.log(pages);
        expect(pages).toEqual([0, 1, 2, 3, 4, 5, 6, 7, 8, 9]);
    });

    it('should display 10 pages from 10', () => {
        const comp = new PagingComponent();
        comp.paging = { size: 10, totalElements: 200, totalPages: 20, number: 11 };
        const pages = comp.computePages();
        expect(pages).toEqual([10, 11, 12, 13, 14, 15, 16, 17, 18, 19]);
    });
});
