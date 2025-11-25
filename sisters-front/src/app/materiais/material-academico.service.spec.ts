import { TestBed } from '@angular/core/testing';

import { MaterialAcademicoService } from './material-academico.service';

describe('MaterialAcademicoService', () => {
  let service: MaterialAcademicoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MaterialAcademicoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
