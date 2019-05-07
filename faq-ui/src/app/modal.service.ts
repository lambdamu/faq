import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

export enum ModalBody {
    Delete,
    UnsavedChanges
}

@Injectable({ providedIn: 'root' })
export class ModalService {
    bodySubject = new Subject<ModalBody>();
    body$: Observable<ModalBody>;
    confirmSubject = new Subject<boolean>();
    confirm$: Observable<boolean>;

    constructor() {
        this.body$ = this.bodySubject.asObservable();
        this.confirm$ = this.confirmSubject.asObservable();
    }

    confirmUnsavedChanges(): Observable<boolean> {
        this.bodySubject.next(ModalBody.UnsavedChanges);
        return this.confirm$;
    }

    confirmDelete(): Observable<boolean> {
        this.bodySubject.next(ModalBody.Delete);
        return this.confirm$;
    }
}
