import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MdunirioComponent } from './mdunirio.component';

describe('MdunirioComponent', () => {
  let component: MdunirioComponent;
  let fixture: ComponentFixture<MdunirioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MdunirioComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MdunirioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
