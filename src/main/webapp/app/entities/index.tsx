import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Categorie from './categorie';
import SousCategorie from './sous-categorie';
import Produit from './produit';
import ProduitUnite from './produit-unite';
import Stock from './stock';
import MouvementStock from './mouvement-stock';
import Client from './client';
import Adresse from './adresse';
import GestionFidelite from './gestion-fidelite';
import Commande from './commande';
import CommandeLignes from './commande-lignes';
import Zone from './zone';
import Livraison from './livraison';
import AffectationZone from './affectation-zone';
import InfoSociete from './info-societe';
import Slides from './slides';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}categorie`} component={Categorie} />
      <ErrorBoundaryRoute path={`${match.url}sous-categorie`} component={SousCategorie} />
      <ErrorBoundaryRoute path={`${match.url}produit`} component={Produit} />
      <ErrorBoundaryRoute path={`${match.url}produit-unite`} component={ProduitUnite} />
      <ErrorBoundaryRoute path={`${match.url}stock`} component={Stock} />
      <ErrorBoundaryRoute path={`${match.url}mouvement-stock`} component={MouvementStock} />
      <ErrorBoundaryRoute path={`${match.url}client`} component={Client} />
      <ErrorBoundaryRoute path={`${match.url}adresse`} component={Adresse} />
      <ErrorBoundaryRoute path={`${match.url}gestion-fidelite`} component={GestionFidelite} />
      <ErrorBoundaryRoute path={`${match.url}commande`} component={Commande} />
      <ErrorBoundaryRoute path={`${match.url}commande-lignes`} component={CommandeLignes} />
      <ErrorBoundaryRoute path={`${match.url}zone`} component={Zone} />
      <ErrorBoundaryRoute path={`${match.url}livraison`} component={Livraison} />
      <ErrorBoundaryRoute path={`${match.url}affectation-zone`} component={AffectationZone} />
      <ErrorBoundaryRoute path={`${match.url}info-societe`} component={InfoSociete} />
      <ErrorBoundaryRoute path={`${match.url}slides`} component={Slides} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
