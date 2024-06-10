import React, {useEffect, useState} from 'react';
import cl from "../../../../styles/Bids.module.css";
import BidHeader from "./BidHeader";
import BidPlace from "./BidPlace";

const Bids = ({auctionDetails, placeBidCallback, auctionComplete, completeCallback, showToolTipCallBack}) => {

    return (
        <div className={cl.container}>
            <BidHeader auctionDetails={auctionDetails} auctionComplete={auctionComplete}/>
            <BidPlace
                auctionDetails={auctionDetails}
                auctionComplete={auctionComplete}
                placeBidCallback={placeBidCallback}
                completeCallback={completeCallback}
                showToolTipCallBack={showToolTipCallBack}/>
        </div>
    );
};

export default Bids;