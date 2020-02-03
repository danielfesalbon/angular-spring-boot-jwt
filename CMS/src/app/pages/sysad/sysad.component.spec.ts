import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SysadComponent } from './sysad.component';

describe('SysadComponent', () => {
  let component: SysadComponent;
  let fixture: ComponentFixture<SysadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SysadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SysadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
