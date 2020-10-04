import React from 'react';
import { Translate, translate } from 'react-jhipster';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Label, Alert, Row, Col } from 'reactstrap';
import { AvForm, AvField, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Link } from 'react-router-dom';
import { Grid } from '@material-ui/core';

export interface ILoginModalProps {
  showModal: boolean;
  loginError: boolean;
  handleLogin: Function;
  handleClose: Function;
}

class LoginModal extends React.Component<ILoginModalProps> {
  handleSubmit = (event, errors, { username, password, rememberMe }) => {
    const { handleLogin } = this.props;
    handleLogin(username, password, rememberMe);
  };

  render() {
    const { loginError, handleClose } = this.props;

    return (
      <Modal isOpen={this.props.showModal} toggle={handleClose} backdrop="static" id="login-page" autoFocus={false}>
        <ModalHeader id="login-title" toggle={handleClose}>
          <Translate contentKey="login.title">Sign in</Translate>
        </ModalHeader>
        <ModalBody>
          <AvForm onSubmit={this.handleSubmit}>
          <Grid item xs={11} md={12}>
            <Row>
              
              <Col md="10" className="offset-1">
                {loginError ? (
                  <Alert color="danger">
                    <Translate contentKey="login.messages.error.authentication">
                      <strong>Failed to sign in!</strong> Please check your credentials and try again.
                    </Translate>
                  </Alert>
                ) : null}
              </Col>
              <Col md="10" className="offset-1">
                <AvField
                  name="username"
                  placeholder={translate('global.form.email.placeholder')}
                  required
                  errorMessage="Veuillez entrer votre adresse e-mail."
                />
                <AvField
                  name="password"
                  type="password"
                  placeholder={translate('login.form.password.placeholder')}
                  required
                  errorMessage="Veuillez entrer votre mot de passe."
                />
                <AvGroup check inline>
                  <Label className="form-check-label">
                    <AvInput type="checkbox" name="rememberMe" /> <Translate contentKey="login.form.rememberme">Remember me</Translate>
                  </Label>
                </AvGroup>
              </Col>
              <Col md="10" className="offset-1">
                <Button className="btn-submit-bazar" type="submit">
                  <Translate contentKey="login.form.button">Sign in</Translate>
                </Button>
              </Col>
            </Row>
          </Grid> 
          </AvForm>
          <div className="mt-1">&nbsp;</div>
          <Grid item xs={11}>
          <Alert className="offset-1  info-login-modal">
            <Link to="/account/reset/request">
              <Translate contentKey="login.password.forgot">Did you forget your password?</Translate>
            </Link>
          </Alert>
          </Grid>
          <Grid item xs={11}>
            <Alert className="offset-1  info-login-modal">
              <span>
                <Translate contentKey="global.messages.info.register.noaccount">You don&apos;t have an account yet?</Translate>
              </span>{' '}
              <Link to="/account/register">
                <Translate contentKey="global.messages.info.register.link">Register a new account</Translate>
              </Link>
            </Alert>
          </Grid>
          
        </ModalBody>
      </Modal>
    );
  }
}

export default LoginModal;
