import "~/CSS/jobPosting.css"
import "~/CSS/InternMapHomepage.css";
import {IndexFooter, IndexHeader} from "./fragments/IndexHeaderAndFooter";
import {Tabs} from "@heroui/react";

// @ts-ignore
export default function Welcome({roadmaps, jobPostings}) {
    return (

        <>
          <IndexHeader/>

          <div className="wrapper">

            <div id="bb1" className="box">
              <div id="bh">
                Explore Roadmaps
              </div>

              <Tabs className="max-w">
                <Tabs.ListContainer>
                  <Tabs.List aria-label="View Selector">

                    <Tabs.Tab id="Roadmap">
                      Roadmaps
                      <Tabs.Indicator/>
                    </Tabs.Tab>

                    <Tabs.Tab id="JobPostings">
                      Job Postings
                      <Tabs.Indicator/>
                    </Tabs.Tab>
                  </Tabs.List>
                </Tabs.ListContainer>

                <Tabs.Panel id="Roadmap">

                  <div className="button-grid">

                    {/*<a /*th:href="@{/new/roadmap}" className="grid-button" /*th:if="${role == 'admin'}"></a>
                      <a th:href="@{/{id}(id=${roadmap.id})}" class="grid-button" th:each="roadmap : ${roadmaps}" th:text="${roadmap.name}" /*style="text-decoration: none;"></a>
                      <a th:if="${roadmaps.isEmpty and role != 'admin'}"*/ /*style="text-decoration: none; font-size: 40px; font-weight: bold; color: #3e3e3e; display: flex; justify-content: center;"></a>*/}

                    {roadmaps.length == 0 ?
                        <a> No roadmaps to show </a> :
                        <>
                          {roadmaps.map((roadmap: Roadmap) => (
                              <a className="grid-button" key={roadmap.id} style={{textDecoration: "none"}}>{roadmap.name}</a>
                          ))}
                        </>
                    }

                  </div>
                  <div /*th:replace="~{fragments/indexFooter}"*/></div>

                </Tabs.Panel>

                  <Tabs.Panel className="grid" id="JobPostings">
                      <div id="box">

                        {jobPostings.map((posting: JobPosting) => (
                            <>
                              <div id="Title">{posting.jobName}</div>
                                <div id="info">
                                {/*"Here's to the crazy ones, the misfits, the rebels, the troublemakers, the round pegs in the square*/}
                                {/*holes... the ones who see things differently – they're not fond of rules... You can quote them,*/}
                                {/*disagree with them, glorify or vilify them, but the only thing you can't do is ignore them because*/}
                                {/*they change things... they push the human race forward, and while some may see them as the crazy ones,*/}
                                {/*we see genius, because the ones who are crazy enough to think that they can change the world, are the*/}
                                {/*ones who do."*/}
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
                                  <div id="bottom" /*style="justify-items: right; display: flex; justify-content: flex-end"*/>
                                    <a /*th:href="@{/applications/new(jobId=${job.id})}" style="text-decoration: none"
                                       th:if="${isRecruiter == null && isAdmin == null}"*/>
                                      <button id="apply-button">Apply</button>
                                    </a>
                                  </div>
                                </div>
                              </>
                        ))}

                      </div>
                  </Tabs.Panel>
              </Tabs>
            </div>
          </div>

            <div id="notificationBox" style={{position: "fixed", top: "20px", right: "20px", background: "#333", color: "white", padding: "10px 20px", borderRadius: "30px", display: "none", zIndex: 1000}}></div>

          <IndexFooter/>
        </>
  );
}