import { Component, ViewChild, OnInit, OnDestroy } from '@angular/core';
import { ModalBody, ModalService } from '../modal.service';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { Subscription } from 'rxjs';

/**
 * Modal component listening to the modal service for
 * modal requests.
 */
@Component({
    selector: 'app-modal',
    templateUrl: './modal.component.html'
})
export class ModalComponent implements OnInit, OnDestroy {
    @ViewChild(ModalDirective) modal: ModalDirective;
    bodySelector = ModalBody;
    bodyValue: ModalBody;
    subscription: Subscription;
    confirmed: boolean;

    constructor(private modalService: ModalService) {
    }

    ngOnInit() {
        this.subscription = this.modalService.body$.subscribe(body => {
                this.bodyValue = body;
                this.confirmed = false;
                this.modal.show();
         });
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

    onHidden() {
        this.modalService.confirmSubject.next(this.confirmed);
    }

    decline() {
        this.confirmed = false;
        this.modal.hide();
    }

    confirm() {
        this.confirmed = true;
        this.modal.hide();
    }
}
