import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CommandeLignes from './commande-lignes';
import CommandeLignesDetail from './commande-lignes-detail';
import CommandeLignesUpdate from './commande-lignes-update';
import CommandeLignesDeleteDialog from './commande-lignes-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CommandeLignesDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CommandeLignesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CommandeLignesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CommandeLignesDetail} />
      <ErrorBoundaryRoute path={match.url} component={CommandeLignes} />
    </Switch>
  </>
);

export default Routes;
