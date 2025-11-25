import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AcolhimentoComponent } from './acolhimento.component';

describe('AcolhimentoComponent', () => {
  let component: AcolhimentoComponent;
  let fixture: ComponentFixture<AcolhimentoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AcolhimentoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AcolhimentoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
