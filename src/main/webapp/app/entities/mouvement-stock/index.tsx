import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MouvementStock from './mouvement-stock';
import MouvementStockDetail from './mouvement-stock-detail';
import MouvementStockUpdate from './mouvement-stock-update';
import MouvementStockDeleteDialog from './mouvement-stock-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MouvementStockDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MouvementStockUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MouvementStockUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MouvementStockDetail} />
      <ErrorBoundaryRoute path={match.url} component={MouvementStock} />
    </Switch>
  </>
);

export default Routes;
