import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarritoProductosComponent } from './carrito-productos.component';

describe('CarritoProductosComponent', () => {
  let component: CarritoProductosComponent;
  let fixture: ComponentFixture<CarritoProductosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CarritoProductosComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CarritoProductosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
