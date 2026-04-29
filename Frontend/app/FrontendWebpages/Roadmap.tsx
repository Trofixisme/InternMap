import {useEffect, useState} from "react";
import {Card} from "@heroui/react";


// @ts-ignore
function Roadmap({roadmap}: {roadmap: Roadmap} ){
return(
    <div>
        <Card>
            <Card.Header>
                <Card.Title>
                    {roadmap.name}
                </Card.Title>
            </Card.Header>

    </Card>
    </div>
);
}
export default Roadmap
