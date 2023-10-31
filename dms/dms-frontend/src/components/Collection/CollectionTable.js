import React, { useEffect, useState } from 'react';
import { Table, Button } from 'react-bootstrap';

function CollectionTable () {
    const [data, setJsonData] = useState([]);
    
    useEffect(() => {
      // Make API call to fetch data
      // Replace the API_URL with your actual API endpoint
      fetch('http://localhost:3001/collections/list')
        .then(response => response.json())
        .then(data => { 
              setJsonData(data.message);
          })
        .catch(error => console.log(error));
    }, []);
  
    const tableStyles = {
      fontFamily: 'Source Sans Pro, sans-serif',
      fontSize: '14px',
      textAlign: 'center',
      border: '0.5px solid',
      width: '95%',
      margin: 'auto',
      marginTop: '30px'
    }

    return (
  
      <Table striped bordered hover style={tableStyles}>
        <thead>
          <tr>
            <th style={{ width: '10%' }}>Collection Name</th>
            <th style={{ width: '40%' }}>Description</th>
            <th style={{ width: '10%' }}>Place</th>
            <th>Icon</th>
            <th>Thumbnail</th>
          </tr>
        </thead>
        <tbody>
          {
              data.map((item, index) => (
                  <tr key={index}>
                      <td>{item.name}</td>
                      <td style={{ textAlign: 'left' }}>{item.description}</td>
                      <td>{item.place}</td>
                      <td>
                      <img style={{ width: 300, height: 250 }} src={item.icon} alt="Non Icon" />
                      </td>
                      <td>
                      <img style={{ width: 300, height: 250 }} src={item.thumbnail} alt="Non Thumbnail" />
                      </td>
                  </tr>))
          }
        </tbody>
      </Table>
      
    );
}

export { CollectionTable }