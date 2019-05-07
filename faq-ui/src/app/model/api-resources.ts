export interface Faq {
    uid: number;
    question: string;
    answer: string;
    tagset: string[];
    _links: {self: {href: string}};
}

export interface Faqs {
    faqResources: Faq[];
}

export interface Page<T> {
    _embedded: T;
    _links: {self: {href: string}};
    /**
     * Page size.
     */
    page: Paging;
}

export interface Paging {
    /**
     * Page size.
     */
    size: number;
    totalElements: number;
    totalPages: number;
    /**
     * Page index starting from 0.
     */
    number: number;
}

export interface Principal {
    name: string;
    authenticated: boolean;
    authorities: Authority[];
}

export interface Authority {
    authority: string;
}
