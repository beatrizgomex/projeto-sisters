import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MaterialAcademicoFormComponent } from './material-academico-form.component';

describe('MaterialAcademicoFormComponent', () => {
  let component: MaterialAcademicoFormComponent;
  let fixture: ComponentFixture<MaterialAcademicoFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MaterialAcademicoFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MaterialAcademicoFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
