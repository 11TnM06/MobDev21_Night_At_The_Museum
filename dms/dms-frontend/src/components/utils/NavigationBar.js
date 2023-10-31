import React from 'react';
import { Navbar, Nav } from 'react-bootstrap';
import { Simulate } from 'react-dom/test-utils';
import { useNavigate } from "react-router-dom";

function NavigationBar () {
  const navigate = useNavigate();
  const linkStyles = {
    fontFamily: 'Source Sans Pro, sans-serif',
    marginRight: '80px',
    fontSize: '18px',
    padding: '10px',
  };


  return (
    <Navbar bg="dark" expand="lg" variant="dark">
      <Navbar.Brand href="#home"></Navbar.Brand>
      <Navbar.Toggle aria-controls="basic-navbar-nav" />
      <Navbar.Collapse id="basic-navbar-nav">
        <Nav className="mx-auto " style={{textAlign: 'center',}}>
          <Nav.Link href="/items" style={linkStyles}>Item</Nav.Link>
          <Nav.Link href="/collections" style={linkStyles}>Collection</Nav.Link>
          <Nav.Link href="/exhibitions" style={linkStyles}>Exhibition</Nav.Link>
          <Nav.Link href="/stories" style={linkStyles}>Story</Nav.Link>
        </Nav>
      </Navbar.Collapse>
    </Navbar>
  );
};

export { NavigationBar }