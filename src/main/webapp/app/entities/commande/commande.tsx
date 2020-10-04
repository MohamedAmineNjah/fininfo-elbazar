import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table, Badge, Toast, ToastBody } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import {
  Translate,
  translate,
  ICrudSearchAction,
  ICrudGetAllAction,
  TextFormat,
  getSortState,
  JhiPagination,
  JhiItemCount,
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities, updateEntity, getFilteredEntities } from './commande.reducer';
import { ICommande } from 'app/shared/model/commande.model';
import { Alert } from '@material-ui/lab';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { updateBeforeStatus, updateNextStatus, updateCancelStatus, generateNextSatusLabel } from './commande-services';
import { StatCmd } from 'app/shared/model/enumerations/stat-cmd.model';
import { TextField, Select, MenuItem } from '@material-ui/core';
import {
  handleFilterIDChange,
  handleSelectValueChanged,
  handleNumberSelectChanged,
  getFilterRequestString,
} from 'app/shared/util/entity-filter-services';
import { useStyles } from 'app/shared/util/entity-style';

export interface ICommandeProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Commande = (props: ICommandeProps) => {
  const [search, setSearch] = useState('');
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const [filter, setfilter] = useState('');
  const classes = useStyles();

  const filterDefaultValue = {
    reference: { fieldName: 'reference', selecType: '.contains', inputValue: '' },
    statut: { fieldName: 'statut', selecType: '.equals', inputValue: '' },
    origine: { fieldName: 'origine', selecType: '.equals', inputValue: '' },
    zoneNom: { fieldName: 'zoneNom', selecType: '.contains', inputValue: '' },
    totTTC: { fieldName: 'totTTC', selecType: '.equals', inputValue: '' },
    nomClient: { fieldName: 'nomClient', selecType: '.contains', inputValue: '' },
    dateLivraison: { fieldName: 'dateLivraison', selecType: '.equals', inputValue: '' },
    dateCreation: { fieldName: 'dateCreation', selecType: '.equals', inputValue: '' },
    dateAnnulation: { fieldName: 'dateAnnulation', selecType: '.equals', inputValue: '' },
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

  const { commandeList, PrixTotalCommandes, match, loading, totalItems } = props;
  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="commande-heading">
            <Translate contentKey="elbazarApp.commande.home.title">Commandes</Translate>
          </h2>
        </Col>
        <Col md="3">
          <h2 id="commande-heading">Total : {PrixTotalCommandes} TND</h2>
        </Col>
      </Row>
      <div className="table-responsive">
        <Table responsive hover size="sm">
          <thead style={{ whiteSpace: 'nowrap' }}>
            <tr>
              <th className="hand">
                <div style={{ display: 'block' }} onClick={sort('reference')}>
                  <Translate contentKey="elbazarApp.commande.reference">Reference</Translate> <FontAwesomeIcon icon="sort" />
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
              <th className="hand">
                <div style={{ display: 'block' }} onClick={sort('statut')}>
                  <Translate contentKey="elbazarApp.commande.statut">Statut</Translate> <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <Select
                    id="comparator-select"
                    name="statut"
                    value={filterValues.statut.inputValue}
                    onChange={e => handleSelectValueChanged(e, filterValues, setfilterValues)}
                    style={{ width: 150 }}
                  >
                    <MenuItem key={0} value={'Reservee'}>
                      Commandée
                    </MenuItem>
                    <MenuItem key={1} value={'Commandee'}>
                      Confirmée
                    </MenuItem>
                    <MenuItem key={0} value={'Livraison'}>
                      En cours
                    </MenuItem>
                    <MenuItem key={0} value={'Livree'}>
                      Livrée
                    </MenuItem>
                    <MenuItem key={0} value={'Annulee'}>
                      Annulée
                    </MenuItem>
                  </Select>
                </div>
              </th>
              <th className="hand">
                <div style={{ display: 'block' }} onClick={sort('origine')}>
                  <Translate contentKey="elbazarApp.commande.origine">Origine</Translate> <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <Select
                    id="comparator-select"
                    name="origine"
                    value={filterValues.origine.inputValue}
                    onChange={e => handleSelectValueChanged(e, filterValues, setfilterValues)}
                    style={{ width: 80 }}
                  >
                    <MenuItem key={0} value={'Client'}>
                      Client
                    </MenuItem>
                    <MenuItem key={1} value={'Admin'}>
                      Admin
                    </MenuItem>
                  </Select>
                </div>
              </th>
              <th className="hand">
                <div style={{ display: 'block' }} onClick={sort('dateLivraison')}>
                  <Translate contentKey="elbazarApp.commande.dateLivraison">Date De Livraison</Translate> <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <Select
                    id="comparator-select"
                    name="dateLivraison"
                    value={filterValues.dateLivraison.selecType}
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
                    name="dateLivraison"
                    type="date"
                    onChange={e => handleFilterIDChange(e, filterValues, setfilterValues)}
                    value={filterValues.dateLivraison.inputValue}
                    style={{ width: 140 }}
                  />
                </div>
              </th>
              <th className="hand">
                <div style={{ display: 'block' }} onClick={sort('zone')}>
                  <Translate contentKey="elbazarApp.commande.zone">Zone</Translate> <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <TextField
                    id="filter-id"
                    name="zoneNom"
                    onChange={e => handleFilterIDChange(e, filterValues, setfilterValues)}
                    value={filterValues.zoneNom.inputValue}
                  />
                </div>
              </th>
              <th className="hand">
                <div style={{ display: 'block' }} onClick={sort('totTTC')}>
                  <Translate contentKey="elbazarApp.commande.totTTC">Tot TTC</Translate> <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <Select
                    id="comparator-select"
                    name="totTTC"
                    value={filterValues.totTTC.selecType}
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
                    name="totTTC"
                    onChange={e => handleFilterIDChange(e, filterValues, setfilterValues)}
                    value={filterValues.totTTC.inputValue}
                    style={{ width: 80 }}
                  />
                </div>
              </th>
              <th className="hand" onClick={sort('devise')}>
                <Translate contentKey="elbazarApp.commande.devise">Devise</Translate> <FontAwesomeIcon icon="sort" />
              </th>
              <th className="hand">
                <div style={{ display: 'block' }} onClick={sort('dateCreation')}>
                  <Translate contentKey="elbazarApp.commande.dateCreation">Date Creation</Translate> <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <Select
                    id="comparator-select"
                    name="dateCreation"
                    value={filterValues.dateCreation.selecType}
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
                    name="dateCreation"
                    type="date"
                    onChange={e => handleFilterIDChange(e, filterValues, setfilterValues)}
                    value={filterValues.dateCreation.inputValue}
                    style={{ width: 140 }}
                  />
                </div>
              </th>
              <th className="hand">
                <div style={{ display: 'block' }} onClick={sort('dateAnnulation')}>
                  <Translate contentKey="elbazarApp.commande.dateAnnulation">Date Annulation</Translate> <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <Select
                    id="comparator-select"
                    name="dateAnnulation"
                    value={filterValues.dateAnnulation.selecType}
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
                    name="dateAnnulation"
                    type="date"
                    onChange={e => handleFilterIDChange(e, filterValues, setfilterValues)}
                    value={filterValues.dateAnnulation.inputValue}
                    style={{ width: 140 }}
                  />
                </div>
              </th>
              <th className="hand">
                <div style={{ display: 'block' }}>
                  Client <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <TextField
                    id="filter-id"
                    name="nomClient"
                    onChange={e => handleFilterIDChange(e, filterValues, setfilterValues)}
                    value={filterValues.nomClient.inputValue}
                  />
                </div>
              </th>
              <th style={{ textAlign: 'center' }}>
                <div style={{ display: 'block' }}>
                  <Button type="reset" className="input-group-addon" onClick={filterData}>
                    <FontAwesomeIcon icon="search" />
                  </Button>
                  <Button type="reset" className="input-group-addon" onClick={clear}>
                    <FontAwesomeIcon icon="trash" />
                  </Button>
                </div>
                <div style={{ display: 'block' }}>Gestion Statut</div>
              </th>
              <th />
            </tr>
          </thead>
          {commandeList && commandeList.length > 0 ? (
            <tbody>
              {commandeList.map((commande, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${commande.id}`} color="link" size="sm">
                      {commande.reference}
                    </Button>
                  </td>
                  <td>
                    {commande.statut === 'Livree' ? (
                      <h4>
                        <Badge color="success">
                          {' '}
                          <Translate contentKey={`elbazarApp.StatCmd.${commande.statut}`} />{' '}
                        </Badge>{' '}
                      </h4>
                    ) : commande.statut === 'Annulee' ? (
                      <h4>
                        <Badge color="primary">
                          {' '}
                          <Translate contentKey={`elbazarApp.StatCmd.${commande.statut}`} />{' '}
                        </Badge>{' '}
                      </h4>
                    ) : (
                      <Translate contentKey={`elbazarApp.StatCmd.${commande.statut}`} />
                    )}
                  </td>
                  <td>
                    <Translate contentKey={`elbazarApp.Origine.${commande.origine}`} />
                  </td>
                  <td>
                    {commande.dateLivraison ? (
                      <TextFormat type="date" value={commande.dateLivraison} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{commande.zoneNom ? <Link to={`zone/${commande.zoneId}`}>{commande.zoneNom}</Link> : ''}</td>
                  <td>{commande.totTTC}</td>
                  <td>
                    <Translate contentKey={`elbazarApp.Devise.${commande.devise}`} />
                  </td>

                  <td>
                    {commande.dateCreation ? <TextFormat type="date" value={commande.dateCreation} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {commande.dateAnnulation ? (
                      <TextFormat type="date" value={commande.dateAnnulation} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {commande.idClientId ? (
                      <Link to={`client/${commande.idClientId}/edit`}>{commande.nomClient + ' ' + commande.prenomClient}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td style={{ textAlign: 'center' }}>
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${commande.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      {commande['statut'] === 'Livraison' ? (
                        <div>
                          {props.updating === false ? (
                            <Button color="warning" onClick={() => updateBeforeStatus(commande, props.updateEntity)} size="sm">
                              <span className="d-none d-md-inline">{'Confirmer'}</span>
                            </Button>
                          ):(
                            <Button color="warning" onClick={() => updateBeforeStatus(commande, props.updateEntity)} size="sm" disabled>
                              <span className="d-none d-md-inline">{'Confirmer'}</span>
                            </Button>
                          ) }
                        
                        </div>
                      ) : (
                        ''
                      )}
                      &nbsp;
                      {generateNextSatusLabel(commande) === 'Aucune Action' || props.updating=== true ? (
                        <Button color="warning" onClick={() => updateNextStatus(commande, props.updateEntity)} size="sm" disabled>
                          <span className="d-none d-md-inline">{generateNextSatusLabel(commande)}</span>
                        </Button>
                      ) : (
                        <Button color="warning" onClick={() => updateNextStatus(commande, props.updateEntity)} size="sm">
                          <span className="d-none d-md-inline">{generateNextSatusLabel(commande)}</span>
                        </Button>
                      )}
                      &nbsp;
                      {commande['statut'] === 'Annulee' || commande['statut'] === 'Livree' || commande['statut'] === 'Livraison' ||  props.updating=== true ? (
                        <Button color="primary" onClick={() => updateCancelStatus(commande, props.updateEntity)} size="sm" disabled>
                          <span className="d-none d-md-inline">{'Annuler'}</span>
                        </Button>
                      ) : (
                        <Button color="primary" onClick={() => updateCancelStatus(commande, props.updateEntity)} size="sm">
                          <span className="d-none d-md-inline">Annuler</span>
                        </Button>
                      )}
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          ) : (
            !loading && (
              <div className={classes.root}>
                <Alert severity="info">
                  <strong>Aucune Commande Trouvé</strong>
                </Alert>
              </div>
            )
          )}
        </Table>
      </div>
      {props.totalItems ? (
        <div className={commandeList && commandeList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ commande }: IRootState) => ({
  commandeList: commande.entities,
  loading: commande.loading,
  totalItems: commande.totalItems,
  updating:commande.updating,
  PrixTotalCommandes: commande.totaPrixCommandesTTC,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
  getFilteredEntities,
  updateEntity,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Commande);
