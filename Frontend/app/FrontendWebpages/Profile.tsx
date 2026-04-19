import { Tabs } from "@heroui/react";
import {IndexHeader} from "~/FrontendWebpages/fragments/IndexHeaderAndFooter";

// @ts-ignore
export default function Profile({userDetails}) {
    return (
        <>
            <IndexHeader/>
            <div className="pl-17 pt-8">
                <div className="flex items-center gap-4 flex-row">
                    <img src="/images/assets/img.png" style={{display: "flex", width: "100px", height: "100px", borderRadius: "100%"}}/>
                    <div>
                        <p className="auto-capitalise text-3xl font-bold">{userDetails.fname + " " + userDetails.lname}</p>
                        <p>{userDetails.email}</p>
                    </div>
                </div>
            </div>

            <div className="grid grid-cols-2 gap-4">
                <div className="p-20 centered-container-full-width">
                    <Tabs className="w-full max-w-md" orientation="vertical">
                        <Tabs.ListContainer>
                            <Tabs.List aria-label="Options">
                                <Tabs.Tab
                                    href="/docs/react/getting-started"
                                    id="getting-started"
                                    
                                >
                                    Getting Started
                                    <Tabs.Indicator />
                                </Tabs.Tab>
                                <Tabs.Tab
                                    href="/docs/react/components"
                                    id="components"
                                    
                                >
                                    Components
                                    <Tabs.Indicator />
                                </Tabs.Tab>
                                <Tabs.Tab
                                    href="/docs/react/releases"
                                    id="releases"
                                >
                                    Releases
                                    <Tabs.Indicator />
                                </Tabs.Tab>
                            </Tabs.List>
                        </Tabs.ListContainer>
                    </Tabs>
                </div>

                <div>

                    <div className="p-20 centered-container-full-width">
                        <label className="label-small">Role</label>
                        <p className="auto-capitalise">{userDetails.role.toString().toLowerCase()}</p>
                    </div>


                    {/*// <!-- Student Fields -->*/}
                    { userDetails.role == "STUDENT" && (
                        <>
                            <h4 className="label-large">Student Details</h4>

                            <div>
                                <label className="label-small">Major</label>
                                <p className="auto-capitalise">{userDetails.studentMajor}</p>
                            </div>

                            <div className="mb-3">
                                <label className="label-small">Year</label>
                                <p className="auto-capitalise">{userDetails.graduatingYear}</p>
                            </div>

                            <div className="mb-3">
                                <label className="label-small">University</label>
                                <p className="auto-capitalise">{userDetails.uniName}</p>
                            </div>

                            {/*// <!-- CV Section -->*/}
                            <h4 className="label-large">Curriculum Vitae</h4>

                            {userDetails.cv ? (
                                <>
                                    <div>

                                        <div>
                                            <label className="label-small">Professional Summary</label>
                                            <p className="auto-capitalise">{userDetails.cv.description}</p>
                                        </div>

                                        <div>
                                            <label className="label-small">Past Experiences</label>
                                            <p style={{whiteSpace: "pre-wrap"}}>{userDetails.cv.pastExperiences}</p>
                                        </div>

                                        <div>
                                            <label className="label-small">Projects</label>
                                            <p style={{whiteSpace: "pre-wrap"}}>{userDetails.cv.projects}</p>
                                        </div>

                                    </div>

                                    <div>
                                        {userDetails.role === 'STUDENT' && (
                                            <a href="/cv" className="btn btn-warning btn-sm">
                                                <i className="bi bi-pencil"></i> Edit CV
                                            </a>
                                        )}
                                    </div>
                                </>
                            ) : (
                                <div>
                                    <p className="text-muted mb-3">You haven't created a CV yet.</p>
                                    <a href="/cv" className="btn btn-success">
                                        <i className="bi bi-plus-circle"></i> Create CV
                                    </a>
                                </div>
                            )}
                        </>
                    )}

                    {/*// <!-- Recruiter Fields -->*/}
                    {userDetails.role == "RECRUITER" && (
                        <>
                            <h4 className="label-large">Recruiter Details</h4>

                            <div>
                                <label className="label-small">Position</label>
                                <p className="auto-capitalise">{userDetails.title}</p>
                            </div>

                            <div>
                                <label className="label-small">Companies</label>
                                <br/><br/>

                                {/*// <!-- If a recruiter has one or more companies -->*/}
                                {userDetails.companies && userDetails.companies.length > 0 ? (
                                    <table className="table">
                                        <thead>
                                        <tr>
                                            <th className="inner-table">Company Name</th>
                                            <th className="inner-table">Industry</th>
                                        </tr>
                                        </thead>

                                        <tbody className="inner-table">
                                        {userDetails.companies.map((company: Company, index: number) => (
                                            <tr key={index} className="inner-table">
                                                <td className="inner-table">{company.name}</td>
                                                <td className="inner-table">{company.industry}</td>
                                            </tr>
                                        ))}
                                        </tbody>
                                    </table>
                                ) : (
                                    <p className="auto-capitalise">
                                        — You're not working for any company.
                                    </p>
                                )}
                            </div>
                        </>
                    )}
                    {/*                // <!-- Admin Fields -->
                // <!--        <div th:if="${type == 'admin'}">-->
                // <!--            <h4 className="text-primary mb-3">Admin Details</h4>-->
                // <!--        </div>-->
                // <!--        <hr>-->*/}

                    <br/>

                </div>
            </div>

        </>
    )
}