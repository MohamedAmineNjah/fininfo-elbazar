import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SousCategorie from './sous-categorie';
import SousCategorieDetail from './sous-categorie-detail';
import SousCategorieUpdate from './sous-categorie-update';
import SousCategorieDeleteDialog from './sous-categorie-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SousCategorieDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SousCategorieUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SousCategorieUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SousCategorieDetail} />
      <ErrorBoundaryRoute path={match.url} component={SousCategorie} />
    </Switch>
  </>
);

export default Routes;
