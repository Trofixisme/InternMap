import "~/CSS/jobPosting.css"
import "~/CSS/InternMapHomepage.css";
import {IndexFooter, IndexHeader} from "./fragments/IndexHeaderAndFooter";
import {Tabs} from "@heroui/react";

// @ts-ignore
export default function RoadMapView({roadmaps}) {
    const roadMapId = new URL(window.location.href).searchParams.get("roadmapID");
    // console.log("roadMapId:", roadMapId);
    // console.log("roadMapId:", roadmaps.find(r => String(r.id) === roadMapId)); // leme test that don't delete

    const selectedRoadmap = roadmaps?.find(r => String(r.id) === roadMapId);

    console.log("roadMapId:", selectedRoadmap.name); // leme test that don't delete

    return(
        <>
            <IndexHeader/>

            <div className="wrapper">
                {selectedRoadmap ? (
                    <div>
                        <h1>{selectedRoadmap.name}</h1>

                        {selectedRoadmap.allModules?.map((module, index) => (
                            <div className="module" key={module.id ?? index}>
                                <div className="module-title">{module.name}</div>
                                <div className="module-description">{module.description}</div>

                                <div className="skills-grid">
                                    {module.allSkills?.map((skill, skillIndex) => (
                                        <div className="skill-card" key={skill.id ?? skillIndex}>
                                            <div className="skill-name">{skill.name}</div>
                                            <div className="skill-description">{skill.description}</div>

                                            <div className="resource-links">
                                                {skill.resourceLinks?.map((link, linkIndex) => (
                                                    <a key={linkIndex} href={link} className="resource-link" target="_blank" rel="noreferrer">
                                                        Resource {linkIndex + 1}
                                                    </a>
                                                ))}
                                            </div>
                                        </div>
                                    ))}
                                </div>
                            </div>
                        ))}
                    </div>
                ) : (
                    <p>Roadmap not found</p>
                )}
            </div>

            <IndexFooter/>
        </>
    );
}