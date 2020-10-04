import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GestionFidelite from './gestion-fidelite';
import GestionFideliteDetail from './gestion-fidelite-detail';
import GestionFideliteUpdate from './gestion-fidelite-update';
import GestionFideliteDeleteDialog from './gestion-fidelite-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GestionFideliteDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GestionFideliteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GestionFideliteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GestionFideliteDetail} />
      <ErrorBoundaryRoute path={match.url} component={GestionFidelite} />
    </Switch>
  </>
);

export default Routes;
