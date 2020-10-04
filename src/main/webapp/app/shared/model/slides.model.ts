export interface ISlides {
  id?: number;
  nom?: string;
  imageContentType?: string;
  image?: any;
  type?: string;
  lien?: string;
}

export const defaultValue: Readonly<ISlides> = {};
