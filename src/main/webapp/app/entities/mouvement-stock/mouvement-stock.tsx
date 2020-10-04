import React, { Component, useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps, Switch } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import {
  Translate,
  translate,
  ICrudSearchAction,
  ICrudGetAllAction,
  TextFormat,
  getSortState,
  IPaginationBaseState,
  JhiPagination,
  JhiItemCount,
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { TabContent, TabPane, Nav, NavItem, NavLink } from 'reactstrap';
import classnames from 'classnames';
import { Stock } from 'app/entities/stock/stock';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities, getFilteredEntities } from './mouvement-stock.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import {
  getFilterRequestString,
  handleSelectValueChanged,
  handleFilterIDChange,
  handleNumberSelectChanged,
} from 'app/shared/util/entity-filter-services';
import { Select, MenuItem, TextField } from '@material-ui/core';

export interface IMouvementStockProps extends Component, StateProps, DispatchProps, RouteComponentProps<{ url: string }> { }

export const MouvementStock = (props: IMouvementStockProps) => {
  const [search, setSearch] = useState('');
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const [filter, setfilter] = useState('');

  const filterDefaultValue = {
    type: { fieldName: 'type', selecType: '.equals', inputValue: '' },
    quantite: { fieldName: 'quantite', selecType: '.equals', inputValue: '' },
    date: { fieldName: 'date', selecType: '.equals', inputValue: '' },
    refProduitReference: { fieldName: 'refProduitReference', selecType: '.contains', inputValue: '' },
    nomProduit: { fieldName: 'nomProduit', selecType: '.contains', inputValue: '' },
    reference: { fieldName: 'reference', selecType: '.contains', inputValue: '' },
    refCommandeReference: { fieldName: 'refCommandeReference', selecType: '.contains', inputValue: '' },
    refCommandeStatut: { fieldName: 'refCommandeStatut', selecType: '.equals', inputValue: '' },
  };

  const [filterValues, setfilterValues] = useState(filterDefaultValue);

  const getAllEntities = () => {
    if (filter) {
      props.getFilteredEntities(
        filter,
        paginationState.activePage - 1,
        paginationState.itemsPerPage,
        `${paginationState.sort},${paginationState.order}`
      );
    } else {
      props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
    }
  };

  const clear = () => {
    setfilterValues(filterDefaultValue);
    setfilter('');
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    props.getEntities();
  };

  const filterData = () => {
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    const filtervalue = getFilterRequestString(filterValues);
    props.getFilteredEntities(
      filtervalue,
      paginationState.activePage - 1,
      paginationState.itemsPerPage,
      `${paginationState.sort},${paginationState.order}`
    );
    setfilter(filtervalue);
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort, search]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const showMvntStockLabel = mvntType => {
    switch (mvntType) {
      case 'EntreeStock':
        return 'Entrée en Stock';
      case 'SortieStock':
        return 'Sortie Stock Correction';
      case 'Commande':
        return 'Commande Front';
      default:
        return '';
    }
  };

  const showCmdStatusLabel = CmdStatus => {
    switch (CmdStatus) {
      case 'Reservee':
        return 'Commandée';
      case 'Commandee':
        return 'Confirmée';
      case 'Livree':
        return 'Livrée';
      case 'Livraison':
        return 'En cours de livraison';
      case 'Annulee':
        return 'Annulée';
      default:
        return '';
    }
  };

  const [activeTab, setActiveTab] = useState('2');

  const toggle = tab => {
    if (activeTab !== tab) setActiveTab(tab);
  };

  const { mouvementStockList, match, loading, totalItems } = props;
  return (
    <div>
      <div>
        <Nav tabs>
          <NavItem>
            <NavLink
              className={classnames({ active: activeTab === '1' })}
              onClick={() => {
                toggle('1');
              }}
              tag={Link}
              to="/stock"
            >
              Stocks
            </NavLink>
          </NavItem>
          <NavItem>
            <NavLink
              className={classnames({ active: activeTab === '2' })}
              onClick={() => {
                toggle('2');
              }}
              tag={Link}
              to="/mouvement-stock"
            >
              Mouvement Stocks
            </NavLink>
          </NavItem>
        </Nav>
        <Switch>
          <TabContent activeTab={activeTab}>
            <TabPane tabId="1">
              <ErrorBoundaryRoute path={`/stock`} component={Stock} />
            </TabPane>
            <TabPane tabId="2"></TabPane>
          </TabContent>
        </Switch>
      </div>
      <h2 id="mouvement-stock-heading">
        <Link to={`${match.url}/new`} className="btn btn-success float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Mouvement Stock
        </Link>
      </h2>
      <div className="table-responsive">
        <Table responsive>
          <thead>
            <tr>
              <th className="hand">
                <div style={{ display: 'block' }} onClick={sort('type')}>
                  <Translate contentKey="elbazarApp.mouvementStock.type">Type</Translate> <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <Select
                    id="comparator-select"
                    name="type"
                    value={filterValues.type.inputValue}
                    onChange={e => handleSelectValueChanged(e, filterValues, setfilterValues)}
                    style={{ width: 150 }}
                  >
                    <MenuItem key={0} value={'EntreeStock'}>
                      Entrée Stock
                    </MenuItem>
                    <MenuItem key={1} value={'SortieStock'}>
                      Sotrie Stock Correction
                    </MenuItem>
                    <MenuItem key={1} value={'Commande'}>
                      Commande Front
                    </MenuItem>
                  </Select>
                </div>
              </th>
              <th className="hand">
                <div style={{ display: 'block' }} onClick={sort('date')}>
                 Date de création<FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <Select
                    id="comparator-select"
                    name="date"
                    value={filterValues.date.selecType}
                    onChange={e => handleNumberSelectChanged(e, filterValues, setfilterValues)}
                  >
                    <MenuItem key={0} value={'.greaterThan'}>
                      &gt;
                    </MenuItem>
                    <MenuItem key={1} value={'.lessThan'}>
                      &lt;
                    </MenuItem>
                    <MenuItem key={2} value={'.equals'}>
                      =
                    </MenuItem>
                  </Select>
                  <TextField
                    id="filter-id"
                    name="date"
                    type="date"
                    onChange={e => handleFilterIDChange(e, filterValues, setfilterValues)}
                    value={filterValues.date.inputValue}
                    style={{ width: 140 }}
                  />
                </div>
              </th>

              <th className="hand">
                <div style={{ display: 'block' }} onClick={sort('quantite')}>
                  <Translate contentKey="elbazarApp.mouvementStock.quantite">Quantite</Translate> <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <Select
                    id="comparator-select"
                    name="quantite"
                    value={filterValues.quantite.selecType}
                    onChange={e => handleNumberSelectChanged(e, filterValues, setfilterValues)}
                  >
                    <MenuItem key={0} value={'.greaterThan'}>
                      &gt;
                    </MenuItem>
                    <MenuItem key={1} value={'.lessThan'}>
                      &lt;
                    </MenuItem>
                    <MenuItem key={2} value={'.equals'}>
                      =
                    </MenuItem>
                  </Select>
                  <TextField
                    id="filter-id"
                    name="quantite"
                    onChange={e => handleFilterIDChange(e, filterValues, setfilterValues)}
                    value={filterValues.quantite.inputValue}
                    style={{ width: 80 }}
                  />
                </div>
              </th>
              <th>
                <div style={{ display: 'block' }}>
                  <Translate contentKey="elbazarApp.mouvementStock.refProduit">Ref Produit</Translate> <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <TextField
                    id="filter-id"
                    name="refProduitReference"
                    onChange={e => handleFilterIDChange(e, filterValues, setfilterValues)}
                    value={filterValues.refProduitReference.inputValue}
                  />
                </div>
              </th>
              <th>
                <div style={{ display: 'block' }}>
                  Nom Produit
                  <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <TextField
                    id="filter-id"
                    name="nomProduit"
                    onChange={e => handleFilterIDChange(e, filterValues, setfilterValues)}
                    value={filterValues.nomProduit.inputValue}
                  />
                </div>
              </th>
              <th>
                <div style={{ display: 'block' }}>
                  Ref Commande<FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <TextField
                    id="filter-id"
                    name="refCommandeReference"
                    onChange={e => handleFilterIDChange(e, filterValues, setfilterValues)}
                    value={filterValues.refCommandeReference.inputValue}
                  />
                </div>
              </th>
              <th className="hand">
                <div style={{ display: 'block' }} >
                  Statut Commande <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <Select
                    id="comparator-select"
                    name="refCommandeStatut"
                    value={filterValues.refCommandeStatut.inputValue}
                    onChange={e => handleSelectValueChanged(e, filterValues, setfilterValues)}
                    style={{ width: 150 }}
                  >
                    <MenuItem key={0} value={'Reservee'}>
                      Commandée
                    </MenuItem>
                    <MenuItem key={1} value={'Commandee'}>
                      Confirmé
                    </MenuItem>
                    <MenuItem key={3} value={'Livraison'}>
                      En cours de Livraison
                    </MenuItem>
                    <MenuItem key={2} value={'Livree'}>
                      Livrée
                    </MenuItem>
                    <MenuItem key={4} value={'Annulee'}>
                      Annulée
                    </MenuItem>
                  </Select>
                </div>
              </th>
              <th>
                <div style={{ display: 'block' }}>
                  Origine du mouvement
                  <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <TextField
                    id="filter-id"
                    name="reference"
                    onChange={e => handleFilterIDChange(e, filterValues, setfilterValues)}
                    value={filterValues.reference.inputValue}
                  />
                </div>
              </th>
              <th>
                <Button type="reset" className="input-group-addon" onClick={filterData}>
                  <FontAwesomeIcon icon="search" />
                </Button>
                <Button type="reset" className="input-group-addon" onClick={clear}>
                  <FontAwesomeIcon icon="trash" />
                </Button>
              </th>
            </tr>
          </thead>
          {mouvementStockList && mouvementStockList.length > 0 ? (
            <tbody>
              {mouvementStockList.map((mouvementStock, i) => (
                <tr key={`entity-${i}`}>
                  <td>{showMvntStockLabel(mouvementStock.type)}</td>
                  <td>
                    {mouvementStock.date ? <TextFormat type="date" value={mouvementStock.date} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>

                  <td>{mouvementStock.quantite}</td>
                  <td>
                    {mouvementStock.refProduitReference ? (
                      <Link to={`produit/${mouvementStock.refProduitId}`}>{mouvementStock.refProduitReference}</Link>
                    ) : (
                        ''
                      )}
                  </td>
                  <td>{mouvementStock.nomProduit}</td>
                  <td>
                    {mouvementStock.refCommandeReference ? (
                      <Link to={`commande/${mouvementStock.refCommandeId}`}>{mouvementStock.refCommandeReference}</Link>
                    ) : (
                        ''
                      )}
                  </td>
                  <td>{showCmdStatusLabel(mouvementStock.refCommandeStatut)}</td>
                  <td>{mouvementStock.reference}</td>
                </tr>
              ))}
            </tbody>
          ) : (
              !loading && (
                <div className="alert alert-warning">
                  <Translate contentKey="elbazarApp.mouvementStock.home.notFound">No Mouvement Stocks found</Translate>
                </div>
              )
            )}
        </Table>
      </div>
      {props.totalItems ? (
        <div className={mouvementStockList && mouvementStockList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={props.totalItems}
            />
          </Row>
        </div>
      ) : (
          ''
        )}
    </div>
  );
};

const mapStateToProps = ({ mouvementStock }: IRootState) => ({
  mouvementStockList: mouvementStock.entities,
  loading: mouvementStock.loading,
  totalItems: mouvementStock.totalItems,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
  getFilteredEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MouvementStock);
