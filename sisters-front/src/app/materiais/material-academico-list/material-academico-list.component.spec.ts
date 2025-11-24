import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MaterialAcademicoListComponent } from './material-academico-list.component';

describe('MaterialAcademicoListComponent', () => {
  let component: MaterialAcademicoListComponent;
  let fixture: ComponentFixture<MaterialAcademicoListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MaterialAcademicoListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MaterialAcademicoListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
