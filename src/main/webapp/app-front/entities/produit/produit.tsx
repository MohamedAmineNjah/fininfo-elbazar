import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import {
  openFile,
  byteSize,
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

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './produit.reducer';
import { IProduit } from 'app/shared/model/produit.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IProduitProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Produit = (props: IProduitProps) => {
  const [search, setSearch] = useState('');
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const getAllEntities = () => {
    if (search) {
      props.getSearchEntities(
        search,
        paginationState.activePage - 1,
        paginationState.itemsPerPage,
        `${paginationState.sort},${paginationState.order}`
      );
    } else {
      props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
    }
  };

  const startSearching = () => {
    if (search) {
      setPaginationState({
        ...paginationState,
        activePage: 1,
      });
      props.getSearchEntities(
        search,
        paginationState.activePage - 1,
        paginationState.itemsPerPage,
        `${paginationState.sort},${paginationState.order}`
      );
    }
  };

  const clear = () => {
    setSearch('');
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    props.getEntities();
  };

  const handleSearch = event => setSearch(event.target.value);

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

  const { produitList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="produit-heading">
        <Translate contentKey="elbazarApp.produit.home.title">Produits</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="elbazarApp.produit.home.createLabel">Create new Produit</Translate>
        </Link>
      </h2>
      <Row>
        <Col sm="12">
          <AvForm onSubmit={startSearching}>
            <AvGroup>
              <InputGroup>
                <AvInput
                  type="text"
                  name="search"
                  value={search}
                  onChange={handleSearch}
                  placeholder={translate('elbazarApp.produit.home.search')}
                />
                <Button className="input-group-addon">
                  <FontAwesomeIcon icon="search" />
                </Button>
                <Button type="reset" className="input-group-addon" onClick={clear}>
                  <FontAwesomeIcon icon="trash" />
                </Button>
              </InputGroup>
            </AvGroup>
          </AvForm>
        </Col>
      </Row>
      <div className="table-responsive">
        {produitList && produitList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('reference')}>
                  <Translate contentKey="elbazarApp.produit.reference">Reference</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('nom')}>
                  <Translate contentKey="elbazarApp.produit.nom">Nom</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('codeBarre')}>
                  <Translate contentKey="elbazarApp.produit.codeBarre">Code Barre</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="elbazarApp.produit.description">Description</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('etat')}>
                  <Translate contentKey="elbazarApp.produit.etat">Etat</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('marque')}>
                  <Translate contentKey="elbazarApp.produit.marque">Marque</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('nature')}>
                  <Translate contentKey="elbazarApp.produit.nature">Nature</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('stockMinimum')}>
                  <Translate contentKey="elbazarApp.produit.stockMinimum">Stock Minimum</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('quantiteVenteMax')}>
                  <Translate contentKey="elbazarApp.produit.quantiteVenteMax">Quantite Vente Max</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('horsStock')}>
                  <Translate contentKey="elbazarApp.produit.horsStock">Hors Stock</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('typeService')}>
                  <Translate contentKey="elbazarApp.produit.typeService">Type Service</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('datePremption')}>
                  <Translate contentKey="elbazarApp.produit.datePremption">Date Premption</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('prixHT')}>
                  <Translate contentKey="elbazarApp.produit.prixHT">Prix HT</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tauxTVA')}>
                  <Translate contentKey="elbazarApp.produit.tauxTVA">Taux TVA</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('prixTTC')}>
                  <Translate contentKey="elbazarApp.produit.prixTTC">Prix TTC</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('devise')}>
                  <Translate contentKey="elbazarApp.produit.devise">Devise</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('sourceProduit')}>
                  <Translate contentKey="elbazarApp.produit.sourceProduit">Source Produit</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('rating')}>
                  <Translate contentKey="elbazarApp.produit.rating">Rating</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('eligibleRemise')}>
                  <Translate contentKey="elbazarApp.produit.eligibleRemise">Eligible Remise</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('remise')}>
                  <Translate contentKey="elbazarApp.produit.remise">Remise</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('debutPromo')}>
                  <Translate contentKey="elbazarApp.produit.debutPromo">Debut Promo</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('finPromo')}>
                  <Translate contentKey="elbazarApp.produit.finPromo">Fin Promo</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('image')}>
                  <Translate contentKey="elbazarApp.produit.image">Image</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('creeLe')}>
                  <Translate contentKey="elbazarApp.produit.creeLe">Cree Le</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('creePar')}>
                  <Translate contentKey="elbazarApp.produit.creePar">Cree Par</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('modifieLe')}>
                  <Translate contentKey="elbazarApp.produit.modifieLe">Modifie Le</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('modifiePar')}>
                  <Translate contentKey="elbazarApp.produit.modifiePar">Modifie Par</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="elbazarApp.produit.categorie">Categorie</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="elbazarApp.produit.sousCategorie">Sous Categorie</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="elbazarApp.produit.unite">Unite</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {produitList.map((produit, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${produit.id}`} color="link" size="sm">
                      {produit.id}
                    </Button>
                  </td>
                  <td>{produit.reference}</td>
                  <td>{produit.nom}</td>
                  <td>{produit.codeBarre}</td>
                  <td>{produit.description}</td>
                  <td>{produit.etat ? 'true' : 'false'}</td>
                  <td>{produit.marque}</td>
                  <td>{produit.nature}</td>
                  <td>{produit.stockMinimum}</td>
                  <td>{produit.quantiteVenteMax}</td>
                  <td>{produit.horsStock ? 'true' : 'false'}</td>
                  <td>{produit.typeService ? 'true' : 'false'}</td>
                  <td>
                    {produit.datePremption ? <TextFormat type="date" value={produit.datePremption} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{produit.prixHT}</td>
                  <td>{produit.tauxTVA}</td>
                  <td>{produit.prixTTC}</td>
                  <td>
                    <Translate contentKey={`elbazarApp.Devise.${produit.devise}`} />
                  </td>
                  <td>
                    <Translate contentKey={`elbazarApp.SourcePrd.${produit.sourceProduit}`} />
                  </td>
                  <td>{produit.rating}</td>
                  <td>{produit.eligibleRemise ? 'true' : 'false'}</td>
                  <td>{produit.remise}</td>
                  <td>
                    {produit.debutPromo ? <TextFormat type="date" value={produit.debutPromo} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{produit.finPromo ? <TextFormat type="date" value={produit.finPromo} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>
                    {produit.image ? (
                      <div>
                        {produit.imageContentType ? (
                          <a onClick={openFile(produit.imageContentType, produit.image)}>
                            <img src={`data:${produit.imageContentType};base64,${produit.image}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {produit.imageContentType}, {byteSize(produit.image)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{produit.creeLe ? <TextFormat type="date" value={produit.creeLe} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{produit.creePar}</td>
                  <td>{produit.modifieLe ? <TextFormat type="date" value={produit.modifieLe} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{produit.modifiePar}</td>
                  <td>{produit.categorieNom ? <Link to={`categorie/${produit.categorieId}`}>{produit.categorieNom}</Link> : ''}</td>
                  <td>
                    {produit.sousCategorieNom ? (
                      <Link to={`sous-categorie/${produit.sousCategorieId}`}>{produit.sousCategorieNom}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{produit.uniteCode ? <Link to={`produit-unite/${produit.uniteId}`}>{produit.uniteCode}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${produit.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${produit.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${produit.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="elbazarApp.produit.home.notFound">No Produits found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={produitList && produitList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ produit }: IRootState) => ({
  produitList: produit.entities,
  loading: produit.loading,
  totalItems: produit.totalItems,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Produit);
