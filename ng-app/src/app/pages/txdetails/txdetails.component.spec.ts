import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TxdetailsComponent } from './txdetails.component';

describe('TxdetailsComponent', () => {
  let component: TxdetailsComponent;
  let fixture: ComponentFixture<TxdetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TxdetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TxdetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
