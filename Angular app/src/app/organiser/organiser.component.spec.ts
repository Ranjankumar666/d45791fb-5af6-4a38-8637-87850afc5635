import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganiserComponent } from './organiser.component';

describe('OrganiserComponent', () => {
  let component: OrganiserComponent;
  let fixture: ComponentFixture<OrganiserComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OrganiserComponent]
    });
    fixture = TestBed.createComponent(OrganiserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
