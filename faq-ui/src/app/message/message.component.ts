import { Component, Input } from '@angular/core';

export enum MessageStatus {
    Clear,
    Loading,
    Info,
    Success,
    Failure
}

export enum MessageTemplate {
    ServerMessage,
    InvalidLogin,
    Created,
    Saved,
    Deleted,
}

export class Message {
    private status: MessageStatus;
    private template: MessageTemplate;
    private text: string;

    constructor() {
        this.status = MessageStatus.Clear;
    }

    setLoading() {
        this.status = MessageStatus.Loading;
        this.template = null;
        this.text = null;
    }

    isLoading(): boolean {
        return this.status == MessageStatus.Loading;
    }

    /**
     * @param status - one of failure, success, info
     * @param template - template enum to be used
     */
    setTemplate(status: MessageStatus, template: MessageTemplate) {
        this.status = status;
        this.template = template;
        this.text = null;
    }

    /**
     * @param status - one of failure, success, info
     * @param text - template enum to be used
     */
    setServerMessage(status: MessageStatus, text: string) {
        this.status = status;
        this.template = MessageTemplate.ServerMessage;
        this.text = text;
    }

    clear() {
        this.status = MessageStatus.Clear;
        this.template = null;
        this.text = null;
    }

    getStatus(): MessageStatus { return this.status; }
    getTemplate(): MessageTemplate { return this.template; }
    getText(): string { return this.text; }
}

@Component({
    selector: 'app-message',
    templateUrl: './message.component.html'
})
export class MessageComponent {
    templateSelector = MessageTemplate;
    @Input() message: Message;

    css(): string {
        switch (this.message.getStatus()) {
            case MessageStatus.Failure: return 'alert-danger';
            case MessageStatus.Success: return 'alert-success';
            default: return 'alert-info';
        }
    }

    hasText(): boolean {
        return this.message &&
        this.message.getStatus() !== MessageStatus.Loading &&
        this.message.getStatus() !== MessageStatus.Clear;
    }

    isLoading(): boolean {
        return this.message && this.message.getStatus() === MessageStatus.Loading;
    }

}
