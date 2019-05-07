import { Component } from '@angular/core';
import { AlertService } from '../alert.service';

@Component({
  selector: 'app-alerts',
  templateUrl: './alerts.component.html'
})
export class AlertsComponent {

    constructor(private alertService: AlertService) {}

    clear() {
        this.alertService.clear();
    }

    alerts(): string[] {
        return this.alertService.alerts;
    }
}
