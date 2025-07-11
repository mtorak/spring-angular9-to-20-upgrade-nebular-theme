  import { Directive, Input, ElementRef, HostListener } from '@angular/core';

@Directive({
    selector: 'img[appImgFallback]',
    standalone: false
})
export class ImgFallbackDirective {

  @Input() appImgFallback: string;

  constructor(private eRef: ElementRef) { }

  @HostListener('error')
  loadFallbackOnError() {
    const element: HTMLImageElement = <HTMLImageElement>this.eRef.nativeElement;
    element.src = this.appImgFallback || 'https://avatar.iran.liara.run/public';
  }

}
