import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';
import categories, { CategoryListState } from './category.reducer';
import producthome, { ProduitListState } from './home.reducer';
import administration, { AdministrationState } from '../../modules/administration/administration.reducer';
import userManagement, { UserManagementState } from '../../modules/administration/user-management/user-management.reducer';
import produit, { ProduitState } from '../../modules/products/products.reducer';
import panier, { PanierState } from '../../modules/panier/panier.reducer';
// prettier-ignore
import produitUnite, {
  ProduitUniteState
} from '../../entities/produit-unite/produit-unite.reducer';
import register, { RegisterState } from '../../modules/account/register/register.reducer';

import activate, { ActivateState } from '../../modules/account/activate/activate.reducer';
import password, { PasswordState } from '../../modules/account/password/password.reducer';
import settings, { SettingsState } from '../../modules/account/profil-main/profil-main.reducer';
import passwordReset, { PasswordResetState } from '../../modules/account/password-reset/password-reset.reducer';
// prettier-ignore

// prettier-ignore
import client, {
  ClientState
} from '../../entities/client/client.reducer';
import adresse, { AdresseState } from '../../modules/account/profil/adresse-tab.reducer';
import commande, { CommandeState } from '../../modules/account/profil/commande-tab.reducer';
import livraison2, { livraison2State } from './livraison2.reducer';

/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly panier: PanierState;
  readonly categories: CategoryListState;
  readonly producthome: ProduitListState;
  readonly produit: ProduitState;
  readonly produitUnite: ProduitUniteState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly client: ClientState;
  readonly adresse: AdresseState;
  readonly commande: CommandeState;
  readonly livraison2: livraison2State;

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
  panier,
  categories,
  producthome,
  produit,
  produitUnite,
  activate,
  passwordReset,
  password,
  settings,
  client,
  adresse,
  commande,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
  livraison2,
});

export default rootReducer;
