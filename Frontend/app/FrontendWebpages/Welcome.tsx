import "~/CSS/jobPosting.css"
import "~/CSS/Roadmap.css";
import {IndexHeader} from "./fragments/IndexHeaderAndFooter";
import {ComboBox, Input, ListBox, SearchField} from "@heroui/react";
import {useState} from "react";
import type {Key} from "node:readline";

// @ts-ignore
export default function Welcome({roadmaps, jobPostings}) {

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

            {
                selectedKey == "Roadmaps" ? (
                    <>
                        <label className="container-label">Recents</label>
                        <div className="container-padded rounded-full" style={{display: "grid", justifyContent: "start", gridTemplateColumns: "repeat(auto-fit, minmax(270px, 1fr))", gap: "50px", borderRadius: "80px"}}>

                            {roadmaps.length == 0 ?
                                <a> No roadmaps to show </a> :
                                <>
                                    {roadmaps.map((roadmap: Roadmap) => (
                                        <div className="roadmap-button" key={roadmap.id} >
                                            <a href={`/roadmap/${roadmap.id}`}>
                                                {roadmap.name}
                                            </a>
                                        </div>
                                    ))}
                                </>
                            }

                        </div>

                        <br/><br/><br/>

                        <label className="container-label">All Roadmaps</label>

                        <div className="container-padded rounded-full" style={{borderRadius: "80px"}}>
                        {roadmaps.length == 0 ?
                            <a> No roadmaps to show </a> :
                            <>
                                {roadmaps.map((roadmap: Roadmap) => (
                                    <div className="roadmap-button" key={roadmap.id} >
                                        <a href={`/roadmap/${roadmap.id}`}>
                                            {roadmap.name}
                                        </a>
                                    </div>
                                ))}
                            </>
                        }
                        </div>

                    </>
                ) : (
                    <div id="box">

                        {jobPostings.map((posting: JobPosting) => (
                            <>
                                <div id="Title">{posting.jobName}</div>
                                <div id="info">
                                    {posting.jobDescription}
                                </div>
                                <hr id="separator"/>
                                <div id="details" /*style="font-weight: bold; font-size: 20px;"*/>Details</div>
                                <div className="unordered-list" id="details-specifics">
                                    <ul>
                                        <li>Email: <span>{posting.recruiterEmail}</span></li>
                                        <li>Company: <span>{posting.company.name}</span></li>
                                        {/*// <!--                    <li>Posting Type: <span th:text="${job.jobPostingType}">Full Time</span></li>-->*/}
                                        {/*@ts-ignore*/}
                                        <li>Date Posted: <span>{posting.postingDate}</span></li>
                                        <li>Requirements: <span>{posting.jobRequirements}</span></li>
                                        {/*<li>Job ID: <span>UUID</span></li>*/}
                                    </ul>
                                </div>
                            </>))}
                    </div>)}
        </>
  );
}