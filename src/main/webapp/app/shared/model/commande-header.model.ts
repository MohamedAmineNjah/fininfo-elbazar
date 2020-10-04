export interface ICommandeHeader {

    clientMobile?: number;
    mobile?: number;
    nom?: string;
    prenom?: string;
    reference?: string;
    telephone?: number;
    adresse?: string;

}

export const defaultValue: Readonly<ICommandeHeader> = {};