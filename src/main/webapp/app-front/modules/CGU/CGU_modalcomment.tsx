import React from 'react';
import { Translate, translate } from 'react-jhipster';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Label, Alert, Row, Col } from 'reactstrap';
import { AvForm, AvField, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Link } from 'react-router-dom';
import { Grid } from '@material-ui/core';

export interface ICGUModalProps {
    showModal: boolean;

    handleClose: Function;
}

class CGUModalComment extends React.Component<ICGUModalProps> {
    render() {
        const { handleClose } = this.props;

        return (
            <Modal isOpen={this.props.showModal} toggle={handleClose} backdrop="static" id="login-page" autoFocus={false}>
                <ModalHeader id="login-title" toggle={handleClose}>
                    <h4 style={{ textAlign: 'center' }}> Comment ça marche</h4>
                </ModalHeader>
                <ModalBody>
                    <Row>
                        <Grid alignItems='center' justify='center'  >



                            <Grid className="ps-timeline-sec" style={{ marginBottom: "20px" }} >
                                <div className="container">
                                    <ol className="ps-timeline">
                                        <li>
                                            <div className="img-handler-top">
                                                <img src="content/images/iunscri.png" width='60' alt="" />
                                            </div>
                                            <div className="ps-bot">
                                                <p>inscrivez vous (l&apos;inscription est gratuite)</p>
                                            </div>
                                            <span className="ps-sp-top">01</span>
                                        </li>
                                        <li>
                                            <div className="img-handler-bot">
                                                <img src="content/images/shopping.png" width='150' alt="" />
                                            </div>
                                            <div className="ps-top">
                                                <p>Remplissez votre panier.<br /> (+100 réferences accsessibles sur ordinateur et mobile)</p>
                                            </div>
                                            <span className="ps-sp-bot">02</span>
                                        </li>
                                        <li>
                                            <div className="img-handler-top">
                                                <img src="content/images/paiement.png" width='100' alt="" />
                                            </div>
                                            <div className="ps-bot">
                                                <p>Choisissez votre mode de paiement
                                                         (le paiement sera à la livraison)</p>
                                            </div>
                                            <span className="ps-sp-top">03</span>
                                        </li>
                                        <li>
                                            <div className="img-handler-bot">
                                                <img src="content/images/delivery.png" width='100' alt="" />
                                            </div>
                                            <div className="ps-top">
                                                <p>Recevez votre course.<br />Nous apportons vos courses jusqu&apos;à votre porte</p>
                                            </div>
                                            <span className="ps-sp-bot">04</span>
                                        </li>
                                    </ol>
                                </div>
                            </Grid>


                        </Grid>








                    </Row>
                </ModalBody>
            </Modal>
        );
    }
}

export default CGUModalComment;
