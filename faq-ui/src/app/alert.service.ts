import { Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class AlertService {
    alerts: string[] = [];

    static toAlertString(error: HttpErrorResponse): string {
        console.error(error);
        let log = `${error.status}: ${error.statusText}`;
        if (error.error && error.error.message) {
            log += ` - ${error.error.message}`;
        }
        return log;
    }

    add(alert: string) {
        this.alerts.push(alert);
    }

    clear() {
        this.alerts = [];
    }
}
