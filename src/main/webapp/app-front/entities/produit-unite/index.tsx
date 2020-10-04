import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProduitUnite from './produit-unite';
import ProduitUniteDetail from './produit-unite-detail';
import ProduitUniteUpdate from './produit-unite-update';
import ProduitUniteDeleteDialog from './produit-unite-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProduitUniteDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProduitUniteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProduitUniteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProduitUniteDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProduitUnite} />
    </Switch>
  </>
);

export default Routes;
