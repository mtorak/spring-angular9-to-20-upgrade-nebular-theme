import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ChatBannerComponent } from './chat-banner.component';

describe('ChatBannerComponent', () => {
  let component: ChatBannerComponent;
  let fixture: ComponentFixture<ChatBannerComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ChatBannerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChatBannerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
