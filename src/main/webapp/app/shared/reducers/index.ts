import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import categorie, {
  CategorieState
} from 'app/entities/categorie/categorie.reducer';
// prettier-ignore
import sousCategorie, {
  SousCategorieState
} from 'app/entities/sous-categorie/sous-categorie.reducer';
// prettier-ignore
import produit, {
  ProduitState
} from 'app/entities/produit/produit.reducer';
// prettier-ignore
import produitUnite, {
  ProduitUniteState
} from 'app/entities/produit-unite/produit-unite.reducer';
// prettier-ignore
import stock, {
  StockState
} from 'app/entities/stock/stock.reducer';
// prettier-ignore
import mouvementStock, {
  MouvementStockState
} from 'app/entities/mouvement-stock/mouvement-stock.reducer';
// prettier-ignore
import client, {
  ClientState
} from 'app/entities/client/client.reducer';
// prettier-ignore
import adresse, {
  AdresseState
} from 'app/entities/adresse/adresse.reducer';
// prettier-ignore
import gestionFidelite, {
  GestionFideliteState
} from 'app/entities/gestion-fidelite/gestion-fidelite.reducer';
// prettier-ignore
import commande, {
  CommandeState
} from 'app/entities/commande/commande.reducer';
// prettier-ignore
import commandeLignes, {
  CommandeLignesState
} from 'app/entities/commande-lignes/commande-lignes.reducer';
// prettier-ignore
import zone, {
  ZoneState
} from 'app/entities/zone/zone.reducer';
// prettier-ignore
import livraison, {
  LivraisonState
} from 'app/entities/livraison/livraison.reducer';
// prettier-ignore
import affectationZone, {
  AffectationZoneState
} from 'app/entities/affectation-zone/affectation-zone.reducer';
// prettier-ignore
import infoSociete, {
  InfoSocieteState
} from 'app/entities/info-societe/info-societe.reducer';
// prettier-ignore
import slides, {
  SlidesState
} from 'app/entities/slides/slides.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly categorie: CategorieState;
  readonly sousCategorie: SousCategorieState;
  readonly produit: ProduitState;
  readonly produitUnite: ProduitUniteState;
  readonly stock: StockState;
  readonly mouvementStock: MouvementStockState;
  readonly client: ClientState;
  readonly adresse: AdresseState;
  readonly gestionFidelite: GestionFideliteState;
  readonly commande: CommandeState;
  readonly commandeLignes: CommandeLignesState;
  readonly zone: ZoneState;
  readonly livraison: LivraisonState;
  readonly affectationZone: AffectationZoneState;
  readonly infoSociete: InfoSocieteState;
  readonly slides: SlidesState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  categorie,
  sousCategorie,
  produit,
  produitUnite,
  stock,
  mouvementStock,
  client,
  adresse,
  gestionFidelite,
  commande,
  commandeLignes,
  zone,
  livraison,
  affectationZone,
  infoSociete,
  slides,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
