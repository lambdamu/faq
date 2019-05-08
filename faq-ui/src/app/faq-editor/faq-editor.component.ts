import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, AbstractControl, FormGroup, FormArray, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AppService } from '../app.service';
import { Faq } from '../model/api-resources';
import { ModalBody } from '../modal/modal.component';
import { Message, MessageStatus, MessageTemplate } from '../message/message.component';
import { CanComponentDeactivate } from '../guards/can-deactivate.guard';
import { Observable, Subject, of } from 'rxjs';
import { take, mergeMap, catchError, map } from 'rxjs/operators';

@Component({
    selector: 'app-faq-editor',
    templateUrl: './faq-editor.component.html'
})
export class FaqEditorComponent implements OnInit, CanComponentDeactivate {
    faqForm: FormGroup;
    faq: Faq;
    message = new Message();
    newStore = false;
    tagAutocompleteSource: Observable<string[]>;
    modalBody: ModalBody;
    modalConfirmation = new Subject<boolean>();

    constructor(private api: AppService, private router: Router, private route: ActivatedRoute,
                private formBuilder: FormBuilder) {
        this.faqForm = this.formBuilder.group({
            question: ['', [Validators.required, Validators.maxLength(255)]],
            answer: ['', [Validators.required, Validators.maxLength(255)]],
            newtag: [''],
            tagset: this.formBuilder.array([])
        });

        // Collect tags for auto-complete excluding existing tags
        this.tagAutocompleteSource = Observable.create((observer: any) => {
            observer.next(this.newtag.value);
        })
         .pipe(
            mergeMap((containing: string) =>
                this.api.findTags(containing)
                .pipe(map((tags: string[]) => tags.filter(tag => !this.tagset.value.includes(tag))))),
            catchError((error: HttpErrorResponse) => { this.api.alert(error); return []; })
      );
    }

    ngOnInit() {
        this.init();
    }

    canDeactivate(): Observable<boolean> | boolean {
        if (this.faq != null && this.faqForm.dirty) {
            return this.getModalConfirmation(ModalBody.UnsavedChanges);
        }
        return true;
    }

    private init() {
        this.message.setLoading();
        if (this.route.snapshot.routeConfig.path === 'create') {
            this.router.navigate(['edit', 0], { skipLocationChange: true });
        } else {
            const id = +this.route.snapshot.paramMap.get('id');
            this.api.getFaq(id)
                .subscribe(
                   faq => { this.message.clear(); this.initForm(faq); },
                    (error: HttpErrorResponse) => { this.api.alert(error, this.message); }
             );
        }
    }

    private initForm(faq: Faq) {
        this.faq = faq ? faq : this.api.newFaq();
        this.newStore = this.faq.uid == null;
        this.onReset();
    }

    private addTag(tag: string) {
        if (!this.tagset.value.includes(tag)) {
            this.tagset.push(this.formBuilder.control(tag));
        }
    }

    private create() {
        this.message.setLoading();
        window.scroll(0, 0);
        this.api.createFaq(this.faq)
            .subscribe(
                faq => {
                    window.history.pushState({}, '', `edit/${faq.uid}`);
                    this.message.setTemplate(MessageStatus.Success, MessageTemplate.Created);
                    this.initForm(faq);
                },
                (error: HttpErrorResponse) => { this.api.alert(error, this.message); }
            );
    }

    private update() {
        this.message.setLoading();
        window.scroll(0, 0);
        this.api.updateFaq(this.faq)
            .subscribe(
                faq => {
                    this.message.setTemplate(MessageStatus.Success, MessageTemplate.Saved);
                    this.initForm(faq);
                },
                (error: HttpErrorResponse) => { this.api.alert(error, this.message); }
            );
    }

    private delete() {
        this.message.setLoading();
        window.scroll(0, 0);
        this.api.deleteFaq(this.faq)
            .subscribe(
                _ => {
                    this.message.setTemplate(MessageStatus.Success, MessageTemplate.Deleted);
                    this.faq = null;
                },
                (error: HttpErrorResponse) => { this.api.alert(error, this.message); }
            );
    }

    get question(): AbstractControl { return this.faqForm.get('question'); }

    get answer(): AbstractControl { return this.faqForm.get('answer'); }

    get newtag(): AbstractControl { return this.faqForm.get('newtag'); }

    get tagset(): FormArray { return this.faqForm.get('tagset') as FormArray; }

    onDeleteTag(i: number) {
        this.tagset.removeAt(i);
        this.tagset.markAsDirty();
    }

    isValidNewTag(): boolean {
        return this.newtag.value && this.newtag.value.trim().length > 0;
    }

    onAddNewTag(event?: KeyboardEvent) {
        this.addTag(this.newtag.value);
        this.newtag.setValue('');
        if (event) {
            event.preventDefault();
        }
    }

    onSave() {
        this.faq.question = this.question.value;
        this.faq.answer = this.answer.value;
        this.faq.tagset = this.tagset.value;
        this.newStore ? this.create() : this.update();
    }

    onReset() {
        for (let i = 0; i < Math.max(this.faq.tagset.length, this.tagset.length); i++) {
            if (i >= this.tagset.length) {
                this.addTag(this.faq.tagset[i]);
            } else if (i >= this.faq.tagset.length) {
                this.tagset.removeAt(i);
            } else {
                this.tagset.at(i).setValue(this.faq.tagset[i]);
            }
        }
        this.question.setValue(this.faq.question);
        this.answer.setValue(this.faq.answer);
        this.faqForm.markAsPristine();
    }

    onDelete() {
        if (!this.newStore) {
            this.getModalConfirmation(ModalBody.Delete)
                .subscribe((confirmed: boolean) => {
                    if (confirmed) {
                        this.delete();
                    }
                });
        }
    }

    getModalConfirmation(body: ModalBody): Observable<boolean> {
        if (this.modalBody == null) {
            this.modalBody = body;
            return this.modalConfirmation.asObservable().pipe(take(1));
        }
        return of(false);
    }

    onModalConfirmation(confirmed: boolean) {
        this.modalBody = null;
        this.modalConfirmation.next(confirmed);
    }

}
