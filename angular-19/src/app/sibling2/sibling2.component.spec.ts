import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Sibling2Component } from './sibling2.component';

describe('Sibling2Component', () => {
  let component: Sibling2Component;
  let fixture: ComponentFixture<Sibling2Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Sibling2Component]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Sibling2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
