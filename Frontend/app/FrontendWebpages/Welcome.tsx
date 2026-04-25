import "~/CSS/jobPosting.css"
import "~/CSS/Roadmap.css";
import {IndexHeader} from "./fragments/IndexHeaderAndFooter";
import {Button, ComboBox, Disclosure, Input, ListBox, SearchField, Separator} from "@heroui/react";
import React, {useState} from "react";
import type {Key} from "node:readline";

let constJobPostings: JobPosting[] = [];
let constRoadmaps: Roadmap[] = [];

// @ts-ignore
export default function Welcome({roadmaps, jobPostings}: {roadmaps: Roadmap[], jobPostings: JobPosting[]}) {

    constRoadmaps = roadmaps.slice(0, roadmaps.length);
    // constJobPostings = jobPostings.slice(0, jobPostings.length);

    roadmaps = constRoadmaps;
    jobPostings = constJobPostings;

    const [isAllExpanded, setAllExpanded] = useState(true);
    const [isRecentsExpanded, setRecentsExpanded] = useState(true);

    let recentRoadmaps: Roadmap[] = [];

    roadmaps.reverse();

    if (roadmaps.length > 3) {
        for (let i = 0; i  < 4; i++) {
            recentRoadmaps.push(roadmaps.shift() as Roadmap)
        }
    } else {
        recentRoadmaps = roadmaps;
    }

    // @ts-ignore
    const [selectedKey, setSelectedKey] = useState<Key | null>("Roadmaps");

    return (

        <>
          <IndexHeader/>

            <div className="flex flex-col items-center justify-center gap-3">
              <div className="flex justify-center justify-self-center flex-row items-center text-4xl font-bold mb-4 gap-3">
                Explore
                  <ComboBox onSelectionChange={(key) => setSelectedKey(key)} defaultValue="Roadmaps" className="w-full max-w-46">
                  <ComboBox.InputGroup>
                      <Input defaultValue="Roadmaps" className="text-2xl" style={{boxShadow: "none", paddingLeft: "27px", caretColor: "transparent"}} />
                      <ComboBox.Trigger />
                  </ComboBox.InputGroup>
                  <ComboBox.Popover>
                      <ListBox>
                          <ListBox.Item id="Roadmaps" textValue="Roadmaps" >
                              Roadmaps
                          </ListBox.Item>
                          <ListBox.Item id="Jobs" textValue="Jobs">
                              Jobs
                          </ListBox.Item>
                      </ListBox>
                  </ComboBox.Popover>
              </ComboBox>
              </div>

                <div className="flex justify-center self-center-safe items-center w-2/6 gap-3">
                    <SearchField name="search" variant="secondary" className="w-full">
                        <SearchField.Group className="rounded-4xl pt-6 pb-6">
                            <SearchField.SearchIcon style={{height: "18px", width: "18px", marginLeft: "14px"}} />
                            <SearchField.Input className="w-full" style={{fontSize: "15px"}} placeholder="Search..." />
                            <SearchField.ClearButton />
                        </SearchField.Group>
                    </SearchField>
                </div>
                <br/>
            </div>


            {selectedKey == "Roadmaps" ? (
                    <>
                        {constRoadmaps.length != 0 && (<div style={{display: "flex", flexDirection: "row", gap: "10px", alignItems: "center"}}>
                            <label className="container-label">Recents</label>
                            <Button style={{width: "32px", height: "32px", background: "var(--secondary-background-color)"}} className="dark" onClick={() => {
                                setRecentsExpanded(!isRecentsExpanded)
                                document.getElementById("recents-chevron")?.classList.toggle("rotate-270")

                            }} isIconOnly>
                                <img id={"recents-chevron"} src={"/images/assets/chevron@4x.png"} style={{width: "16px", filter: "invert(0.3)"}} alt="pencil"/>
                            </Button>
                        </div>)}

                        {roadmaps.length == 0 ?
                            <div className="flex items-center justify-center" style={{height: "47vh"}}>
                            <a className="label-placeholder"> No roadmaps to show </a>
                            </div> :
                            <>

                                <Disclosure isExpanded={isRecentsExpanded}>
                                    <Disclosure.Content className="overflow-visible">
                                        <Disclosure.Body className="overflow-visible">

                                        <div className="container-padded" style={{display: "grid", justifyContent: "start", gridTemplateColumns: "repeat(auto-fit, minmax(270px, 1fr))", gap: "50px", borderRadius: "50px"}}>
                                {recentRoadmaps.map((roadmap: Roadmap) => (
                                    <div className="roadmap-button min-w-72" key={roadmap.id} >
                                        <a href={`/roadmaps/${roadmap.id}`}>
                                            {roadmap.name}
                                        </a>
                                    </div>
                                ))}
                                </div>

                                        </Disclosure.Body>
                                    </Disclosure.Content>
                                </Disclosure>
                            </>
                        }

                        <br/><br/><br/>

                        {constRoadmaps.length != 0 && (<div style={{display: "flex", flexDirection: "row", gap: "10px", alignItems: "center"}}>
                            <label className="container-label">All Roadmaps</label>
                            <Button style={{width: "32px", height: "32px", background: "var(--secondary-background-color)"}} className="dark" onClick={() => {
                                setAllExpanded(!isAllExpanded)
                                document.getElementById("all-chevron")?.classList.toggle("rotate-270")
                            }} isIconOnly>
                                <img id="all-chevron" src="/images/assets/chevron@4x.png" style={{width: "16px", filter: "invert(0.3)"}} alt="pencil"/>
                            </Button>
                        </div>)}

                        <Disclosure isExpanded={isAllExpanded}>
                            <Disclosure.Content className="overflow-visible">
                                <Disclosure.Body className="overflow-visible">

                                        {roadmaps.length == 0 ?
                                            <></> :
                                            <>
                                                <div className="container-padded" style={{display: "grid", justifyContent: "start", gridTemplateColumns: "repeat(auto-fit, minmax(270px, 1fr))", gap: "50px", borderRadius: "50px"}}>
                                                    {roadmaps.map((roadmap: Roadmap) => (
                                                        <div className="roadmap-button min-w-72" key={roadmap.id} >
                                                            <a href={`/roadmap/${roadmap.id}`}>
                                                                {roadmap.name}
                                                            </a>
                                                        </div>
                                                    ))}
                                                </div>
                                            </>
                                        }


                                </Disclosure.Body>
                            </Disclosure.Content>
                        </Disclosure>
                        <br/><br/><br/>
                    </>
                ) : (

                    <div style={{display: "grid", justifyContent: "start", gridTemplateColumns: "repeat(auto-fit, minmax(270px, 1fr))", gap: "50px", borderRadius: "50px"}}>

                        {jobPostings.map((posting: JobPosting) => (
                            <div className="container">
                                <div>{posting.jobName}</div>
                                <h2 style={{lineClamp: 1, display: "-webkit-box", WebkitLineClamp: 1, WebkitBoxOrient: "vertical", overflow: "hidden"}}>
                                    {posting.jobDescription}
                                </h2>
                                <Separator className="border-1" />
                                <div id="details" /*style="font-weight: bold; font-size: 20px;"*/>Details</div>
                                <div className="unordered-list" id="details-specifics">
                                    <ul>
                                        <li>Email: <span>{posting.recruiter.email}</span></li>
                                        <li>Company: <span>{posting.company.name}</span></li>
                                        {/*// <!--                    <li>Posting Type: <span th:text="${job.jobPostingType}">Full Time</span></li>-->*/}
                                        {/*@ts-ignore*/}
                                        <li>Posted <span>{((Date.now() - Date.parse(posting.datePosted).valueOf()) / 1000 / 60 / 60 / 24).toFixed(0)}</span> days ago</li>
                                        <li>Requirements: <span>{posting.jobRequirements}</span></li>
                                        {/*<li>Job ID: <span>UUID</span></li>*/}
                                    </ul>
                                </div>
                            </div>
                        ))}
                    </div>
            )}
        </>
  );
}