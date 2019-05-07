import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, AbstractControl, FormGroup, FormArray, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AppService } from '../app.service';
import { Faq } from '../model/api-resources';
import { Message, MessageStatus, MessageTemplate } from '../message/message.component';
import { CanComponentDeactivate } from '../guards/can-deactivate.guard';
import { Observable } from 'rxjs';
import { take } from 'rxjs/operators';
import { ModalService } from '../modal.service';

@Component({
    selector: 'app-faq-editor',
    templateUrl: './faq-editor.component.html',
    styleUrls: ['./faq-editor.component.scss']
})
export class FaqEditorComponent implements OnInit, CanComponentDeactivate {
    faqForm: FormGroup;
    faq: Faq;
    message = new Message();
    newStore = false;

    constructor(private api: AppService, private route: ActivatedRoute,
                private formBuilder: FormBuilder, private modalService: ModalService) {
        this.faqForm = this.formBuilder.group({
            question: ['', [Validators.required, Validators.maxLength(255)]],
            answer: ['', [Validators.required, Validators.maxLength(255)]],
            newtag: [''],
            tagset: this.formBuilder.array([])
        });
    }

    ngOnInit() {
        this.init();
    }

    canDeactivate(): Observable<boolean> | boolean {
        if (this.faq != null && this.faqForm.dirty) {
            return this.modalService.confirmUnsavedChanges()
                .pipe(take(1));
        }
        return true;
    }

    private init() {
        this.message.setLoading();
        const id = +this.route.snapshot.paramMap.get('id');
        this.api.getFaq(id)
            .subscribe(
                faq => { this.message.clear(); this.initForm(faq); },
                (error: HttpErrorResponse) => { this.api.alert(error, this.message); }
            );
    }

    private initForm(faq: Faq) {
        this.faq = faq ? faq : this.api.newFaq();
        this.newStore = this.faq.uid == null;
        this.onReset();
    }

    private addTag(tag: string) {
        this.tagset.push(this.formBuilder.control(tag));
    }

    private create() {
        this.message.setLoading();
        this.api.createFaq(this.faq)
            .subscribe(
                faq => {
                    this.message.setTemplate(MessageStatus.Success, MessageTemplate.Created);
                    this.initForm(faq);
                },
                (error: HttpErrorResponse) => { this.api.alert(error, this.message); }
            );
    }

    private update() {
        this.message.setLoading();
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
            return this.modalService.confirmDelete()
                .pipe(take(1))
                .subscribe((confirmed: boolean) => {
                    if (confirmed) {
                        this.delete();
                    }
                });
        }
    }

}
