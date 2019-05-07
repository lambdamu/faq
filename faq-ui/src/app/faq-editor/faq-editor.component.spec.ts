import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FaqEditorComponent } from './faq-editor.component';

describe('FaqEditorComponent', () => {
  let component: FaqEditorComponent;
  let fixture: ComponentFixture<FaqEditorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FaqEditorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FaqEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
