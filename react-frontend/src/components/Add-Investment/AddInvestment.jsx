// src/components/Add-Investment/AddInvestment.jsx
import React from 'react';
import './AddInvestment.css';

const AddInvestment = () => {
  return (
    <div className="add-investment-page">
      <div className="button-section">
        <button className="dashboard-button">Sell Investment</button>
        <button className="dashboard-button">Update Prices</button>
        <button className="dashboard-button">View Gain</button>
        <button className="dashboard-button">Search Portfolio</button>
        <button className="dashboard-button">Set A Goal</button>
      </div>

      <div className="add-investment-container">
        <h2 className="add-investment-title">Add an Investment</h2>
        <div className="form-container">
          <form className="investment-form">
            <div className="form-row">
              <div className="dropdown-container">
                <select className="form-input form-select" defaultValue="Type">
                  <option disabled>Type</option>
                  <option value="stock">Stock</option>
                  <option value="mutual-fund">Mutual Fund</option>
                </select>
              </div>
            </div>

            <div className="form-row">
              <input type="text" placeholder="Symbol" className="form-input" />
              <input type="number" placeholder="Quantity" className="form-input" />
            </div>

            <div className="form-row">
              <input type="text" placeholder="Name" className="form-input" />
              <input type="number" placeholder="Price" className="form-input" />
            </div>

            <div className="button-row">
              <button type="reset" className="reset-button">Reset</button>
              <button type="submit" className="buy-button">Buy</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default AddInvestment;
