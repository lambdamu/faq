import { Component, ViewChild, EventEmitter, Input, Output, OnChanges, SimpleChange } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';


export enum ModalBody {
    Delete,
    UnsavedChanges
}

@Component({
    selector: 'app-modal',
    templateUrl: './modal.component.html'
})
export class ModalComponent implements OnChanges {
    @ViewChild(ModalDirective) modal: ModalDirective;
    bodySelector = ModalBody;
    @Input() body: ModalBody;
    @Output() confirmed =  new EventEmitter<boolean>();
    confirmation = false;

    ngOnChanges(changes: { [propKey: string]: SimpleChange }) {
        for (const propName in changes) {
            if (propName) {
                const propChanged = changes[propName];
                this.body = propChanged.currentValue;
            }
        }
        if (this.body != null) {
            this.confirmation = false;
            this.modal.show();
        }
    }

    onHidden() {
        this.confirmed.emit(this.confirmation);
    }

    decline() {
        this.confirmation = false;
        this.modal.hide();
    }

    confirm() {
        this.confirmation = true;
        this.modal.hide();
    }
}
